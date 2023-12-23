import com.seiko.imageloader.*
import com.seiko.imageloader.component.*
import okio.Path.Companion.toOkioPath
import java.io.*

fun generateImageLoader(): ImageLoader = ImageLoader {
    components {
        setupDefaultComponents()
    }
    interceptor {
        // cache 100 success image result, without bitmap
        defaultImageResultMemoryCache()
        memoryCacheConfig {
            maxSizeBytes(32 * 1024 * 1024) // 32MB
        }
        diskCacheConfig {
            directory(getCacheDir().toOkioPath().resolve("image_cache"))
            maxSizeBytes(512L * 1024 * 1024) // 512MB
        }
    }
}

// about currentOperatingSystem, see app
private fun getCacheDir() = when (currentOperatingSystem) {
    OperatingSystem.Windows -> File(System.getenv("AppData"), "$ApplicationName/cache")
    OperatingSystem.Linux -> File(System.getProperty("user.home"), ".cache/$ApplicationName")
    OperatingSystem.MacOS -> File(System.getProperty("user.home"), "Library/Caches/$ApplicationName")
    else -> throw IllegalStateException("Unsupported operating system")
}