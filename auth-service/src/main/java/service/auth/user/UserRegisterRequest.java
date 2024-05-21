package service.auth.user;


public record UserRegisterRequest(String firstName, String lastName, String username, String password, String roles) {
}