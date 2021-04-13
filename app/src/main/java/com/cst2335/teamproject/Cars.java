package com.cst2335.teamproject;

import org.jetbrains.annotations.Nullable;

public class Cars {
    private String makeName;
    private String modelName;
    private String modelID;
    private String makeID;

    @Nullable
    public final String returnMake() {
        return this.makeName;
    }

    @Nullable
    public final String returnModel() {
        return this.modelName;
    }

    @Nullable
    public final String returnModelID() {
        return this.modelID;
    }

    @Nullable
    public final String returnMakeID() {
        return this.makeID;
    }

    public Cars(@Nullable String makeID, @Nullable String makeName, @Nullable String modelId, @Nullable String modelName) {
        this.makeID = makeID;
        this.makeName = makeName;
        this.modelID = modelId;
        this.modelName = modelName;
}}
