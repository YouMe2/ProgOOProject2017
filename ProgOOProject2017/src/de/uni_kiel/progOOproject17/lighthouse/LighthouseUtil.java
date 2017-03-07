package de.uni_kiel.progOOproject17.lighthouse;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;

/**
 *
 * This class provides the {@link #imageToByteArray(BufferedImage)} method.
 *
 */
public class LighthouseUtil {

	/**
	 * returns a byte[] containing the color info from the {@link BufferedImage}
	 * img, which fulfills the specification made by the
	 * {@link LighthouseNetwork}.
	 * 
	 * @param img The img to convert
	 * @return the byte array containing the color values
	 */
	public static byte[] imageToByteArray(BufferedImage img) {
		if (img.getType() != BufferedImage.TYPE_3BYTE_BGR)
			throw new IllegalArgumentException("Invalid image type");
		int w = img.getWidth();
		int h = img.getHeight();
		if (w != 28 || h != 14)
			throw new IllegalArgumentException("Invalid image dimension");
		Raster raster = img.getData();
		DataBuffer dataBuffer = raster.getDataBuffer();
		// TYPE_3BYTE_BGR images should be backed by a byte buffer
		if (!(dataBuffer instanceof DataBufferByte))
			throw new IllegalArgumentException("Invalid buffer type");
		DataBufferByte buf = (DataBufferByte) dataBuffer;
		byte[] res = buf.getData();
		assert res.length == 28 * 14 * 3;
		for (int blueIndex = 0; blueIndex < res.length; blueIndex += 3) {
			// save blue
			byte blue = res[blueIndex];
			// move red
			int redIndex = blueIndex + 2;
			res[blueIndex] = res[redIndex];
			// store blue
			res[redIndex] = blue;
		}
		return res;
	}

}
