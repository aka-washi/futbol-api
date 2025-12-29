package com.eagle.futbolapi.features.organization.entity;

public enum OrganizationType {
    CLUB("Club"),
    FEDERATION("Federation"),
    CONFEDERATION("Confederation"),
    GOVERNINGBODY("Governing Body");

    private final String displayName;

    OrganizationType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static OrganizationType lookupByDisplayName(final String displayName) {
        for (OrganizationType type : values()) {
            if (type.getDisplayName().equals(displayName)) {
                return type;
            }
        }
        return null;
    }
}
