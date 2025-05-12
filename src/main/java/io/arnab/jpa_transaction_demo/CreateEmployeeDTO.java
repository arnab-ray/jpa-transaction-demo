package io.arnab.jpa_transaction_demo;

public record CreateEmployeeDTO(
        String name, String dob, Gender gender
) {
    public enum Gender {
        M, F, O
    }
}
