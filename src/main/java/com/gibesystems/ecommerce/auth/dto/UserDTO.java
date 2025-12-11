package com.gibesystems.ecommerce.auth.dto;

import java.time.LocalDateTime;

import com.gibesystems.ecommerce.auth.entity.UserRole;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private UserRole role;
    private boolean enabled;
    private String groupId;
    private String groupName;
    private LocalDateTime createdAt;

    // For many-to-many
    private java.util.List<String> groupIds;
    private java.util.List<String> groupNames;

    // getters and setters for new fields
    public java.util.List<String> getGroupIds() { return groupIds; }
    public void setGroupIds(java.util.List<String> groupIds) { this.groupIds = groupIds; }
    public java.util.List<String> getGroupNames() { return groupNames; }
    public void setGroupNames(java.util.List<String> groupNames) { this.groupNames = groupNames; }
}