package jp.cafebabe.traverser;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class TraverserBuilder {
    public Traverser build(Path path) throws IOException{
        if(isJarOrZipFile(path))
            return createJarTraverser(path);
        return createDefaultTraverser(path);
    }

    public Traverser2 buildForFileItem(Path path) throws IOException {
        DefaultTraverser traverser = (DefaultTraverser) build(path);
        return wrap(traverser);
    }

    private Traverser2 wrap(DefaultTraverser traverser) {
        return new DefaultTraverser2(traverser);
    }

    private DefaultTraverser createJarTraverser(Path path) throws IOException{
        FileSystem fs = FileSystems
                .newFileSystem(path, getClass().getClassLoader());
        return new DefaultTraverser(fs, fs.getPath("/"), path);
    }

    private DefaultTraverser createDefaultTraverser(Path path) throws IOException{
        FileSystem fs = FileSystems.getDefault();
        return new DefaultTraverser(fs, path, path);
    }

    private boolean isJarOrZipFile(Path path){
        String name = path.toString();
        return name.endsWith(".jar")
                || name.endsWith(".zip");
    }
}
