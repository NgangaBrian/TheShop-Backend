package com.TheShopApp.database.restControllers;

import com.TheShopApp.darajaApi.dtos.*;
import com.TheShopApp.database.models.*;
import com.TheShopApp.database.repository.PaymentRepository;
import com.TheShopApp.database.repository.ProfileDetailsRepository;
import com.TheShopApp.database.repository.UserRepository;
import com.TheShopApp.database.services.*;
import com.TheShopApp.database.utils.GoogleTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class MySQLController {


    private final BannerSliderService bannerSliderService;
    private final CategoriesService categoriesService;
    private final ItemsService itemsService;
    private final UserService userService;
    private final OrderService orderService;
    private final ProfileDetailsRepository profileDetailsRepository;
    private final UserRepository userRepository;
    private final ItemSearchService itemSearchService;
    private final PaymentRepository paymentRepository;

    public MySQLController(UserService userService, ItemsService itemsService, CategoriesService categoriesService,
                           BannerSliderService bannerSliderService, OrderService orderService, ProfileDetailsRepository profileDetailsRepository,
                           UserRepository userRepository, ItemSearchService itemSearchService, PaymentRepository paymentRepository) {
        this.userService = userService;
        this.itemsService = itemsService;
        this.categoriesService = categoriesService;
        this.bannerSliderService = bannerSliderService;
        this.orderService = orderService;
        this.profileDetailsRepository = profileDetailsRepository;
        this.userRepository = userRepository;
        this.itemSearchService = itemSearchService;
        this.paymentRepository = paymentRepository;
    }


    @PostMapping("/user/register")
    public ResponseEntity<String> registerNewUser(@RequestParam("fullname") String fullname,
                                                  @RequestParam("email") String email,
                                                  @RequestParam(value = "password", required = false) String password,
                                                  @RequestParam(value = "googleId", required = false) String idToken,
                                                  @RequestParam("authType") String authType) {
        if(authType.equals("google")) {
            try {
                GoogleIdToken.Payload payload = GoogleTokenVerifier.verifyGoogleToken(idToken);

                String payloadEmail = payload.getEmail();
                String payloadGoogleId = payload.getSubject();
                String payloadName = (String) payload.get("name");

                if (userService.checkGoogleUserExists(payloadGoogleId)) {
                    return ResponseEntity.ok("User already exists");
                } else {
                    userService.registerGoogleUser(payloadName, payloadEmail, payloadGoogleId, authType);
                    return ResponseEntity.ok("Google User registered successfully");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


        if(authType.equals("password")) {
            if(userService.checkUserExists(email)) {
                return ResponseEntity.ok("User already exists");
            } else {
                userService.registerEmailUser(fullname, email, password, authType);
                return ResponseEntity.ok("User registered successfully");
            }
        }
        return ResponseEntity.badRequest().body("Invalid authentication type.");
    }

    @PostMapping("/user/login")
    public ResponseEntity<Map<String, Object>> authenticateUser(@RequestBody Login login){

        Map<String, Object> response = new HashMap<>();

        User user = userService.getUserDetailsByEmail(login.getEmail());

        // Google login authentication
        if ("google".equals(login.getAuthType())) {
            // Handle Google user authentication
            // You should verify the Google ID token here (e.g., using Google's API)
            try {

                GoogleIdToken.Payload payload = GoogleTokenVerifier.verifyGoogleToken(login.getGoogleId());

                String email = payload.getEmail();
                String payloadGoogleId = payload.getSubject();

                boolean userEmail = userService.checkUserExists(email);

                if(!userEmail){
                    response.put("status", "error");
                    response.put("message", "Email does not exist");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }

                if (!payloadGoogleId.equals(user.getGoogleId())) {
                    response.put("status", "error");
                    response.put("message", "Invalid Google authentication");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                response.put("status", "success");
                response.put("user", user);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                response.put("status", "error");
                response.put("message", "Invalid Google authentication!!!!");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }

        // Manual login
        else if ("password".equals(login.getAuthType())) {
            boolean userEmail = userService.checkUserExists(login.getEmail());
            if(!userEmail){
                response.put("status", "error");
                response.put("message", "Email does not exist");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            String hashedPassword = userService.checkUserPasswordByEmail(login.getEmail());
            if (!BCrypt.checkpw(login.getPassword(), hashedPassword)) {
                response.put("status", "error");
                response.put("message", "Incorrect email or password");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.put("status", "success");
            response.put("user", user);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("status", "error");
            response.put("message", "Invalid Auth Type");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }

    @GetMapping("/bannersliders")
    public List<BannerSliderModel> getSliders() {
        return bannerSliderService.getAllBannerSliders();
    }

    @GetMapping("/categories")
    public List<CategoriesModel> getAllCategories() {
        return categoriesService.getAllCategories();
    }

    @GetMapping("/items")
    public ResponseEntity<Page<ItemsModel>> getAllItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "0") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ItemsModel> pagedResult = itemsService.getAllItems(pageable);
        return ResponseEntity.ok(pagedResult);
    }

    @GetMapping("/orders/{customerId}")
    public List<OrderDTO> getOrdersByCustomerId(@PathVariable Long customerId) {
        return orderService.getOrderByCustomerId(customerId);
    }

    @PostMapping("/updateuserdetails")
    public ResponseEntity<AcknowledgeResponse> addProfileDetails(@RequestBody ProfileDetailsModel profileDetailsModel) {
        try {

            Optional<ProfileDetailsModel> existingProfile = profileDetailsRepository.findById(profileDetailsModel.getUser_id());

            if (existingProfile.isPresent()) {
                // Update existing record
                ProfileDetailsModel profileDetails = existingProfile.get();
                profileDetails.setMobile_number(profileDetailsModel.getMobile_number());
                profileDetails.setAddress(profileDetailsModel.getAddress());
                profileDetails.setPostal_code(profileDetailsModel.getPostal_code());
                profileDetails.setProfile_url(profileDetailsModel.getProfile_url());

                profileDetailsRepository.save(profileDetails);

                AcknowledgeResponse response = new AcknowledgeResponse();
                response.setMessage("updated");
                return ResponseEntity.ok(response);
            } else {

                ProfileDetailsModel newProfileDetails = new ProfileDetailsModel();
                newProfileDetails.setUser_id(profileDetailsModel.getUser_id());
                newProfileDetails.setMobile_number(profileDetailsModel.getMobile_number());
                newProfileDetails.setAddress(profileDetailsModel.getAddress());
                newProfileDetails.setPostal_code(profileDetailsModel.getPostal_code());
                newProfileDetails.setProfile_url(profileDetailsModel.getProfile_url());

                profileDetailsRepository.save(newProfileDetails);

                AcknowledgeResponse response = new AcknowledgeResponse();
                response.setMessage("success");
                return ResponseEntity.ok(response);
            }} catch (Exception e) {
            e.printStackTrace();
            AcknowledgeResponse response = new AcknowledgeResponse();
            response.setMessage("failed");

            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/getuserdetails/{customerId}")
    public ResponseEntity<ProfileDetailsModel> getProfileDetails(@PathVariable Integer customerId) {
        try {
            Optional<ProfileDetailsModel> userDetails = profileDetailsRepository.findById(customerId);
            if (userDetails.isPresent()) {
                return ResponseEntity.ok(userDetails.get());
            } else {
                return ResponseEntity.status(404).body(null);
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/user/changepassword")
    public ResponseEntity<AcknowledgeResponse> changePassword(@RequestBody ChangePasswordModel changePassModel){
        AcknowledgeResponse response = new AcknowledgeResponse();
        try {
            Optional<User> userOptional = userRepository.findById(changePassModel.getId());
            if (userOptional.isEmpty()) {
                response.setMessage("User not found");
                return ResponseEntity.status(404).body(response);
            }
            User user = userOptional.get();

            log.info("Retrieved user details: {}", user.getEmail() + " " + user.getPassword() + " " + user.getFullname());

            if (!BCrypt.checkpw(changePassModel.getOldPassword(), user.getPassword())) {
                response.setMessage("Your entered the wrong password");
                return ResponseEntity.ok(response);
            }
            user.setPassword(BCrypt.hashpw(changePassModel.getNewPassword(), BCrypt.gensalt()));
            userRepository.save(user);

            response.setMessage("Password Changed Successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("Error changing password");
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String search) {
        List<SearchItemsModel> products = itemSearchService.searchProduct(search);
        if (products.isEmpty()) {
            return ResponseEntity.status(404).body("Product not found");
        }
        return ResponseEntity.ok(products);
    }

    @PostMapping("/saveOrders")
    public ResponseEntity<?> saveOrderDetails(@RequestBody OrdersModel ordersModel) {
        Long orderId = orderService.saveOrder(ordersModel);

        AcknowledgeResponse response = new AcknowledgeResponse();
        response.setMessage(String.valueOf(orderId));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/savePaymentDetails")
    public ResponseEntity<AcknowledgeResponse> savePaymentDetails(@RequestBody STKPushAsynchronousResponse stkPushAsynchronousResponse) {

        StkCallback stkCallback = stkPushAsynchronousResponse.getBody().getStkCallback();
        String merchantRequestID = stkCallback.getMerchantRequestID();
        String checkoutRequestID = stkCallback.getCheckoutRequestID();
        int resultCode = stkCallback.getResultCode();
        String resultDesc = stkCallback.getResultDesc();

        CallbackMetadata callbackMetadata = stkCallback.getCallbackMetadata();

        // Initialize variables to store details
        String phoneNumer = null;
        BigDecimal amount = null;
        LocalDateTime transDate = null;
        String mpesa_receipt_number = null;

        for (ItemItem item : callbackMetadata.getItem()) {
            switch (item.getName()) {
                case "PhoneNumber":
                    phoneNumer = item.getValue();
                    break;
                case "Amount":
                    amount = new BigDecimal(item.getValue());
                    break;
                case "TransactionDate":
                    transDate = LocalDateTime.parse(item.getValue(), DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                    break;
                case "MpesaReceiptNumber":
                    mpesa_receipt_number = item.getValue();
                    break;
                default:
                    break;
            }
        }

        Payments payments = new Payments();

        payments.setMpesa_receipt_number(mpesa_receipt_number);
        payments.setTrans_date(transDate);
        payments.setPhone_number(phoneNumer);
        payments.setAmount(amount);
        payments.setCheckout_request_id(checkoutRequestID);
        payments.setMerchantRequestId(merchantRequestID);
        payments.setResult_code(resultCode);
        payments.setResult_desc(resultDesc);

        paymentRepository.save(payments);

        AcknowledgeResponse response = new AcknowledgeResponse();
        response.setMessage("Success");

        return ResponseEntity.ok(response);
    }
}
