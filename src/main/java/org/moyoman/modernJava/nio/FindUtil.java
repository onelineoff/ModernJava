package org.moyoman.modernJava.nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

@Component
public class FindUtil {
	public Stream<Path> streamByFileType(String baseDir, String suffix) throws IOException {

		return Files.find(Paths.get(baseDir), Integer.MAX_VALUE, (p, bfa) -> p.endsWith(suffix));
	}
	
	public List<Path> findByContent(Stream<Path> lines, String content) throws IOException {
		return lines.filter(l -> l.getFileName().toString().contains(content)).toList();
	}
}
