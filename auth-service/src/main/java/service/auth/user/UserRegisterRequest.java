package service.auth.user;


public record UserRegisterRequest(String username, String password, String roles) {
}