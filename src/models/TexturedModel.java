package models;

import textures.ModelTexture;

public class TexturedModel {

    private RawModel rawModel;
    private ModelTexture modelTexture;

    public RawModel getRawModel() {
        return rawModel;
    }

    public ModelTexture getTexture() {
        return modelTexture;
    }

    public TexturedModel(RawModel model, ModelTexture texture) {
        this.rawModel = model;
        this.modelTexture = texture;

    }

}
