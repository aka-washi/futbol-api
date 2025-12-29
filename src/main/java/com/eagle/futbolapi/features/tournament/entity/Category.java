package com.eagle.futbolapi.features.tournament.entity;

public enum Category {

    MEN("Men"),
    WOMEN("Women");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Category lookupByDisplayName(final String displayName) {
        for (Category category : Category.values()) {
            if (category.getDisplayName().equalsIgnoreCase(displayName)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No category found with display name: " + displayName);
    }
}
