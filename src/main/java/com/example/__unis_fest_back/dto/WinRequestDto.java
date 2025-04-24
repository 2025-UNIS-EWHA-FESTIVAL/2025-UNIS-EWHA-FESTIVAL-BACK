package com.example.__unis_fest_back.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WinRequestDto {
    @NotNull(message = "주문 번호는 필수입니다.")
    private Integer orderNumber;

    @NotBlank(message = "단과대명은 비어 있을 수 없습니다.")
    @Size(max = 20, message = "단과대명은 20자 이하로 입력해주세요.")
    private String collegeName;

    @NotBlank(message = "전화번호는 비어 있을 수 없습니다.")
    @Pattern(regexp = "^\\d{11}$", message = "전화번호는 숫자만 포함하며 11자리여야 합니다.")
    private String phoneNumber;

    @Size(max = 255, message = "후기 내용은 255자 이하로 작성해주세요.")
    private String reviewText;
}
