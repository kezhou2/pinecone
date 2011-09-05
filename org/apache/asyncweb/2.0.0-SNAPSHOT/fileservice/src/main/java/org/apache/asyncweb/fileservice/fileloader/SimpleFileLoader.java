/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package org.apache.asyncweb.fileservice.fileloader;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.security.InvalidParameterException;

import org.apache.mina.core.buffer.IoBuffer;

/**
 * A simple file loader, supposed to be efficient on relativly small 
 * files.
 * 
 * @author The Apache MINA Project (dev@mina.apache.org)
 */
public class SimpleFileLoader implements FileLoader {

	public IoBuffer loadFile(File file) throws IOException {
		if(file.length()> Integer.MAX_VALUE)
				throw new InvalidParameterException("File "+file+" is too big for SimpleFileLoader try MmapFileLoader");
		IoBuffer buffer=IoBuffer.allocate((int)file.length());
		RandomAccessFile raf = new RandomAccessFile(file, "r");
        FileChannel fc = raf.getChannel();
        fc.read(buffer.buf());
		buffer.flip();
		fc.close();
		raf.close();
		return buffer;
	}
	

}
