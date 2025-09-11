package com.example.demo.dto;

import com.example.demo.entity.Employee;

import java.util.List;

/**
 * 作用:
 *
 * @projectName: day8-springboot-3-layer-error-handle-employee-starter
 * @package: com.example.demo.dto
 * @className: CompanyRequest
 * @author: lhfy
 * @description: TODO
 * @date: 2025/9/11 18:39
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequest {
    private Integer id;
    private String name;
    private List<Employee> employees;
}
