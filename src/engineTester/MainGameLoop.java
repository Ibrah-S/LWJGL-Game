package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import models.RawModel;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

import java.util.ArrayList;
import java.util.List;

public class MainGameLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();

        /*
        RawModel grassModel = OBJLoader.loadObjModel("grassModel", loader);
        TexturedModel grassStaticModel = new TexturedModel(grassModel, new ModelTexture(loader.loadTexture("grassTexture")));
        ModelTexture grassTexture = grassStaticModel.getTexture();
        grassTexture.setReflectivity(0.001f);
        grassTexture.setShineDamper(10);
        grassTexture.setUseFakeLighting(true);
        grassTexture.setHasTransparency(true);

        Entity actualGrass = new Entity(grassStaticModel, new Vector3f(0, 0, 0), 0, 0, 0, 1);
        */

        ModelData dragon = OBJFileLoader.loadOBJ("dragon");
        RawModel dragonModel = loader.loadToVAO(dragon.getVertices(), dragon.getTextureCoords(), dragon.getNormals(), dragon.getIndices());
        TexturedModel dragonStaticModel = new TexturedModel(dragonModel, new ModelTexture(loader.loadTexture("texture")));
        ModelTexture dragonTexture = dragonStaticModel.getTexture();
        dragonTexture.setReflectivity(3);
        dragonTexture.setShineDamper(20);
//        Entity dragonEntity = new Entity(dragonStaticModel, new Vector3f(0, 0, 0), 0, 0, 0, 1f);

        ModelData lampData = OBJFileLoader.loadOBJ("lamp");
        RawModel lampModel = loader.loadToVAO(lampData.getVertices(), lampData.getTextureCoords(), lampData.getNormals(), lampData.getIndices());
        TexturedModel lamp = new TexturedModel(lampModel, new ModelTexture(loader.loadTexture("lamp")));
        ModelTexture lampTexture = lamp.getTexture();
        lampTexture.setReflectivity(1);
        lampTexture.setShineDamper(15);

        ModelData panda1 = OBJFileLoader.loadOBJ("panda");
        RawModel panda1Model = loader.loadToVAO(panda1.getVertices(), panda1.getTextureCoords(), panda1.getNormals(), panda1.getIndices());
        TexturedModel panda1StaticModel = new TexturedModel(panda1Model, new ModelTexture(loader.loadTexture("texture")));
        ModelTexture panda1Texture = panda1StaticModel.getTexture();
        panda1Texture.setReflectivity(1);
        panda1Texture.setShineDamper(10);

        ModelData bunnyModelData = OBJFileLoader.loadOBJ("person");
        RawModel bunnyModel = loader.loadToVAO(bunnyModelData.getVertices(), bunnyModelData.getTextureCoords(), bunnyModelData.getNormals(), bunnyModelData.getIndices());
        TexturedModel stanfordBunny = new TexturedModel(bunnyModel, new ModelTexture(loader.loadTexture("playerTexture")));
        ModelTexture stanfordModel = stanfordBunny.getTexture();
        stanfordModel.setReflectivity(1);
        stanfordModel.setShineDamper(5);

        Player player = new Player(panda1StaticModel, new Vector3f(0, 0, 0), -90, 0, 0, 0.05f);

//        ModelData zebra = OBJFileLoader.loadOBJ("zebra");
//        RawModel zebraModel = loader.loadToVAO(zebra.getVertices(), zebra.getTextureCoords(), zebra.getNormals(), zebra.getIndices());
//        TexturedModel zebraStaticModel = new TexturedModel(zebraModel, new ModelTexture(loader.loadTexture("zebra-atlas")));
//        ModelTexture zebraTexture = zebraStaticModel.getTexture();
//        zebraTexture.setReflectivity(1);
//        zebraTexture.setShineDamper(10);
//        Entity zebraEntity = new Entity(zebraStaticModel, new Vector3f(0, 20, 0), -90, 0, 0, 0.1f);

        ModelTexture grass = new ModelTexture(loader.loadTexture("grass"));
        grass.setReflectivity(1);
        grass.setShineDamper(10);

        List<Light> lights = new ArrayList<>();
        lights.add(new Light(new Vector3f(0,1000,-7000), new Vector3f(0.4f,0.4f,0.4f)));
        lights.add(new Light(new Vector3f(185, 10, -293), new Vector3f(2, 0, 0), new Vector3f(1, 0.01f, 0.002f)));
        lights.add(new Light(new Vector3f(370, 17, -300), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f, 0.002f)));
        lights.add(new Light(new Vector3f(293, 7, -205), new Vector3f(2, 2, 0), new Vector3f(1, 0.01f, 0.002f)));

        List<Terrain> terrains = new ArrayList<>();

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

        terrains.add(new Terrain(0, 0, loader, texturePack, blendMap, "heightmap"));
        terrains.add(new Terrain(0, 1, loader, texturePack, blendMap, "heightmap"));
        terrains.add(new Terrain(0, -1, loader, texturePack, blendMap, "heightmap"));
        terrains.add(new Terrain(1, 0, loader, texturePack, blendMap, "heightmap"));
        terrains.add(new Terrain(1, 1, loader, texturePack, blendMap, "heightmap"));
        terrains.add(new Terrain(1, -1, loader, texturePack, blendMap, "heightmap"));
        terrains.add(new Terrain(-1, 0, loader, texturePack, blendMap, "heightmap"));
        terrains.add(new Terrain(-1, 1, loader, texturePack, blendMap, "heightmap"));
        terrains.add(new Terrain(-1, -1, loader, texturePack, blendMap, "heightmap"));

        List<Entity> entities = new ArrayList<>();
        entities.add(new Entity(panda1StaticModel, new Vector3f(0, 0, 0), -90, 0, 0, 0.05f));
        entities.add(new Entity(lamp, new Vector3f(185, -4.7f, -293), 0, 0, 0, 1));
        entities.add(new Entity(lamp, new Vector3f(370, 4.2f, -300), 0, 0, 0, 1));
        entities.add(new Entity(lamp, new Vector3f(293, -6.8f, -305), 0, 0, 0, 1));

        Camera camera = new Camera(player);

        MasterRenderer renderer = new MasterRenderer();
        while (!Display.isCloseRequested()) {
            camera.move();
            player.move(terrains.get(0));
            renderer.processEntity(player);
            renderer.processTerrains(terrains);
            for (Entity entity : entities) renderer.processEntity(entity);
//            dragonEntity.increaseRotation(0, 0.5f, 0);
//            renderer.processEntity(zebraEntity);
//            zebraEntity.increaseRotation(0, 0, 5f);
//            renderer.processEntity(dragonEntity);
            renderer.render(lights, camera);
            DisplayManager.updateDisplay();
        }
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
