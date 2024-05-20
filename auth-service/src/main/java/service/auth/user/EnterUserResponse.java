package service.auth.user;

public record EnterUserResponse(String statusRole, Long userId, String firstName, String lastName) {}
