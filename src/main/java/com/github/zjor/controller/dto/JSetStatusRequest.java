package com.github.zjor.controller.dto;

import com.github.zjor.domain.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JSetStatusRequest {
    private ItemStatus status;
}
