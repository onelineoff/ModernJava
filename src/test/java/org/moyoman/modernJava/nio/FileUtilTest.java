package org.moyoman.modernJava.nio;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FileUtilTest {
	private final static String baseDir = "/home/daw/technical/code/ModernJava";
	
	@Autowired
	public FindUtil findUtil;
	
	@Test
	public void testStreamByFileType() throws IOException {
		Stream<Path> stream = findUtil.streamByFileType(baseDir, "java");
		Assert.assertNotNull(stream);
	}
	
	@Test
	public void testFindByContent() throws IOException {
		Stream<Path> stream = findUtil.streamByFileType(baseDir, "java");
		List<Path> fileList = findUtil.findByContent(stream, "Path");
		for (Path path : fileList) {
			System.out.println(path.getFileName());
		}
	}
}
