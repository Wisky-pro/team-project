package API;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.time.LocalDate;

import java.io.File;
import java.io.IOException;

public class GitUpdator {
    public static void pushChanges() throws GitAPIException {
        try (Git git = Git.open(new File("."))) {
            git.add().addFilepattern("priceHistory.json").call();
            git.commit().setMessage("priceHistory.json auto update on " + LocalDate.now().toString()).call();
            git.push()
                    .setCredentialsProvider(
                            new UsernamePasswordCredentialsProvider(
                            "Wisky-pro",
                                    System.getenv("GITHUB_TOKEN")
                    ))
                    .call();
            System.out.println("Successfully push on " + LocalDate.now().toString());
        } catch (IOException | GitAPIException e) {
            throw new RuntimeException("Git push failed", e);
        }
    }
}
