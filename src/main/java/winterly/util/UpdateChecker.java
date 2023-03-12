package winterly.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import net.minecraft.text.Text;
import net.minecraft.util.Language;
import winterly.Winterly;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class UpdateChecker {
	private static final Gson GSON = new GsonBuilder().setLenient().setPrettyPrinting().create();
	private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().build();
	private static Instant lastCheck = Instant.now().minus(4, ChronoUnit.HOURS);
	private static String cachedLatest = "0";
	public static boolean updated = false;
	public static Text text = null;

	public static boolean check() {
		if(Winterly.config.updateCheckerEnabled) {
			var local = getLocalVersion();
			var remote = getLatestRemote();

			var localNum = Integer.parseInt(local.replaceAll("\\.", ""));
			var remoteNum = Integer.parseInt(remote.replaceAll("\\.", ""));

			if(remoteNum > localNum) {
				String temp = Language.getInstance().get("itemGroup.winterly.items.button.updated");

				temp = temp.replaceAll("%local_version%", local);
				temp = temp.replaceAll("%remote_version%", remote);

				text = Text.of(temp);
				updated = true;
			}
		}
		return updated;
	}

	static String getLatestRemote() {
		if(ChronoUnit.HOURS.between(lastCheck, Instant.now()) >= 4) {
			String remote = "0";
			var versions = getModrinthVersions();
			if(versions.isPresent() && !versions.get().isEmpty()) {
				remote = versions.get().get(0).versionNumber;
			}
			cachedLatest = remote;
			lastCheck = Instant.now();
		}
		return cachedLatest;
	}

	static String getLocalVersion() {
		String version = "1000";
		var mod = FabricLoader.getInstance().getModContainer("winterly");

		if(mod.isPresent()) {
			version = mod.get().getMetadata().getVersion().getFriendlyString();
		}

		return version;
	}

	static Optional<ArrayList<ProjectVersion>> getModrinthVersions() {
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.GET()
					.uri(new URI("https://api.modrinth.com/v2/project/winterly/version"))
					.headers("User-Agent", "tyap-lyap/winterly/" + getLocalVersion())
					.build();

			HttpResponse<InputStream> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofInputStream());
			InputStreamReader reader = new InputStreamReader(response.body());
			ProjectVersion[] versions = GSON.fromJson(reader, ProjectVersion[].class);

			var array = new ArrayList<ProjectVersion>();

			Arrays.asList(versions).forEach(version -> {
				if(version.gameVersions.contains(SharedConstants.getGameVersion().getName())) array.add(version);
			});

			return Optional.of(array);
		}
		catch(FileNotFoundException e) {
			return Optional.empty();
		}
		catch(Exception e) {
			Winterly.LOGGER.info("Failed to parse modrinth project versions due to an exception:\n" + e);
		}
		return Optional.empty();
	}

	@SuppressWarnings("unused")
	static class ProjectVersion {
		public String id = "";
		@SerializedName("project_id")
		public String projectId = "";
		@SerializedName("author_id")
		public String authorId = "";
		public boolean featured = false;
		public String name = "";
		@SerializedName("version_number")
		public String versionNumber = "";
		public String changelog = "";
		@SerializedName("changelog_url")
		public String changelogUrl = "";
		@SerializedName("date_published")
		public String published = "";
		public int downloads = 0;
		@SerializedName("version_type")
		public String versionType = "";
		@SerializedName("game_versions")
		public ArrayList<String> gameVersions = new ArrayList<>();
		public ArrayList<String> loaders = new ArrayList<>();
	}
}
