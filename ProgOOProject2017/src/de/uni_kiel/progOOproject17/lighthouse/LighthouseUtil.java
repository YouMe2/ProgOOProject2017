package de.uni_kiel.progOOproject17.lighthouse;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;

public class LighthouseUtil {

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
		assert dataBuffer.getDataType() == DataBuffer.TYPE_BYTE;
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
