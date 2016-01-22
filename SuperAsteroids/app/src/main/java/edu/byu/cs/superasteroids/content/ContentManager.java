package edu.byu.cs.superasteroids.content;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to load and unload files, such as images and sounds, into memory. It is also used to
 * copy the asteroids.sqlite database file from the assets folder into the app's databases folder.
 *
 * This class is also used to manage the play back of audio files.
 *
 * Students need not worry about creating an instance of this class. A singleton instance of this
 * class will be passed into all loadContent() functions called by the GameEngine or the
 * ShipBuildingActivity. If a loadContent function is called, use this class's getInstance() function
 * to get the singleton instance of this class.
 */
public class ContentManager {

    //Path to the android app databases folder
	public static String DB_PATH = "/data/data/edu.byu.cs.superasteroids/databases/";
	
	private Map<Integer, Bitmap> images;
	private Map<String, Integer> indexedImages;
	private int idCounter = 0;
	private Resources resources;
	private AssetManager assets;
   // private BitmapFactory.Options options;

    private AudioManagement audio = AudioManagement.getInstance();

    //Singleton instance
	private static volatile ContentManager instance;
	
	private ContentManager() {
		images = new HashMap<>();
		indexedImages = new HashMap<>();
        //options = new BitmapFactory.Options();
        //options.inScaled = false;
        //options.inPreferredConfig = Bitmap.Config.RGB_565;
	}

    /**
     * Gets the singleton instance of this class. Instantiates the instance if it does not exist.
     * @return The only instance of this class
     */
	public static ContentManager getInstance() {
		if(instance == null)
			instance = new ContentManager();
		
		return instance;
	}

    /**
     * Gets the ID of an image if it has already been loaded through this content manager
     * @param imagePath The path of the image to get the ID for. The imagePath should use the
     *                  assets folder as its base. EXAMPLE: A file located in the "assets/images/space.png"
     *                  would have the path as "images/space.png".
     * @return The ID associated with the imagePath if the image has been loaded. -1 if the image
     *         has not been loaded.
     */
	public int getImageId(String imagePath) {
		if(indexedImages.containsKey(imagePath))
			return indexedImages.get(imagePath);
		else 
			return -1;
	}

    /**
     * Loads an image into memory using the provided imagePath. If the image associated with the
     * imagePath has already been loaded, the image is not reloaded and the image id is returned.
     * @param imagePath The path of the image to load. The imagePath should use the assets
     *                  folder as its base. EXAMPLE: A file located in the "assets/images/space.png"
     *                  would have the path as "images/space.png".
     * @return The ID of the image if the image was loaded, -1 otherwise. The image will not load
     *         if it does not exist in the assets folder or an assets subfolder or the path is
     *         incorrect.
     */
	public int loadImage(String imagePath) {
		if(indexedImages.containsKey(imagePath))
			return indexedImages.get(imagePath);
		else {
			
			Bitmap bitmap;
			try {
				bitmap = BitmapFactory.decodeStream(assets.open(imagePath));
				images.put(idCounter, bitmap);
				indexedImages.put(imagePath, idCounter);
				return idCounter++;
			} catch (IOException e) {
				e.printStackTrace();
				return -1;
			}
		}
	}

    /**
     * Loads a list of images into memory.
     * @param imagePaths A list of image paths associated with the desired images to load. Follow the
     *                   image path guidelines in loadImage().
     * @return A list of image IDs associated with the loaded images. NOTE: If any incorrect image
     *         paths are provided, the list will contain some image IDs == -1.
     */
	public List<Integer> loadImages(List<String> imagePaths) {
		List<Integer> imageIds = new ArrayList<>();
		for(String imagePath : imagePaths) {
			imageIds.add(loadImage(imagePath));
		}
		return imageIds;
	}

    /**
     * Loads a drawable image into memory. Students shouldn't worry about using this function.
     * @param drawableId The ID of the drawable image.
     * @return The loaded bitmap of the drawable image.
     */
    public Bitmap loadBitmap(int drawableId) {

        if(images.containsKey(drawableId))
            return images.get(drawableId);
        else {
            Bitmap bitmap = BitmapFactory.decodeResource(resources, drawableId);
            images.put(drawableId, bitmap);
            return bitmap;
        }
    }

    /**
     * Unloads an image from memory if the image exists in memory. If the image does not exist in
     * memory, this function simply returns false as in indicator.
     * @param imageId The ID of the image to unload from memory
     * @return TRUE if the image associated with the provided ID was unloaded, FALSE otherwise
     *
     */
	public boolean unloadImage(int imageId) {
		
		if(images.containsKey(imageId)) {
            images.get(imageId).recycle();
			images.remove(imageId);
			if(indexedImages.containsValue(imageId)) {
				
				for(String key : indexedImages.keySet()) {
					if(indexedImages.get(key) == imageId) {
						indexedImages.remove(key);
						break;
					}
				}
			}
            return true;
		}
		else {
			return false;
		}
	}

    /**
     * Loads a sound effect into memory. Background music files should be loaded with the
     * loadLoopSound() function.
     * @param filePath The path of the sound effect to load. The file path should use the assets
     *                 folder as its base. EXAMPLE: A file located in the "assets/sounds/laser.ogg"
     *                 would have the path as "sounds/laser.ogg".
     * @return The ID of the loaded sound.
     * @throws IOException If the sound file path was incorrect
     */
    public int loadSound(String filePath) throws IOException {
        return audio.loadSound(filePath);
    }

    /**
     * Unloads a sound from memory.
     * @param id The ID of the sound to unload.
     */
    public void unloadSound(int id) {audio.unloadSound(id); }

    /**
     * Loads a looping background sound file into memory. Sound effects should be loaded with the loadSound()
     * function.
     * @param filePath The file path of the background sound file to load. The file path should use the
     *                 assets folder as its base. EXAMPLE: A file located in the "assets/sounds/laser.ogg"
     *                 would have the path as "sounds/laser.ogg".
     * @return The ID of the loop sound
     * @throws IOException
     */
    public int loadLoopSound(String filePath) throws IOException {return audio.loadLoopSound(filePath);}

    /**
     * Unloads a looping background sound file from memory. Sound effects should be unloaded with the unloadSound()
     * function.
     * @param id The ID of the loop sound to unload. If the ID is not associated with a loaded backgound
     *           sound file, nothing will be unloaded from memory.
     */
    public void unloadLoopSound(int id) {audio.unloadLoop(id);}

    /**
     * Plays a looping background sound. If the sound was previously paused, the sound will pick
     * up where it left off at the pause. Does nothing if there is no loaded background sound file
     * associated with the provided ID.
     * @param id The ID of the background sound to play.
     */
    public void playLoop(int id) { audio.playLoop(id);}

    /**
     * Pauses a looping background sound. The audio framework will keep track of where in the
     * playback it was paused. Does nothing if there is no loaded background sound file associated
     * with the provided ID.
     * @param id The ID of the background sound to pause.
     */
    public void pauseLoop(int id) {
        audio.pauseLoop(id);
    }

    /**
     * Resets a looping background sound. This will set playback of the sound back to the beginning.
     * Does nothing if there is no loaded background sound file associated with the provided ID.
     * @param id The ID of the background sound to reset.
     */
    public void resetLoop(int id) {
        audio.resetLoop(id);
    }

    /**
     * Plays a sound effect. Does nothing if there is no loaded sound effect associated with the
     * provided ID.
     * @param soundId The ID of the sound to play.
     * @param volume The volume of the sound. (range = 0.0 to 1.0)
     * @param speed Sound playback speed (1.0 = normal playback, range 0.5 - 2.0)
     * @return TRUE if the sound was played, FALSE otherwise
     */
    public boolean playSound(int soundId, float volume, float speed) {
        return audio.playSound(soundId, volume, speed);
    }

    /**
     * **Students need not worry about this function**
     * Sets the Android app resources of this object
     * @param resources
     */
	public void setResources(Resources resources) {
		this.resources = resources;
	}

    /**
     * **Students need not worry about using this function**
     * Gets a bitmap of a loaded image.
     * @param id The ID of the loaded image
     * @return The bitmap of the loaded image associated with the ID
     */
	public Bitmap getImage(int id) {
		return images.get(id);
	}

    /**
     * **Students need not worry about using this function**
     * Sets the Android app assets of this object
     * @param assets
     */
	public void setAssets(AssetManager assets) {
		this.assets = assets;
        audio.setAssets(assets);
	}
}
