package app.authpass.website

import com.dc2f.ContentDef
import com.dc2f.common.*
import com.dc2f.common.contentdef.BaseWebsite
import com.dc2f.common.theme.baseTheme
import com.dc2f.render.*
import com.dc2f.util.*
import kotlin.reflect.KClass

class WebsiteTheme : Theme() {
    override fun configure(config: ThemeConfig) {
        baseTheme()
    }

}

interface StoreUrls : ContentDef {
    val ios: String
    val android: String
    val macos: String
}

abstract class AuthPassWebsite : BaseWebsite() {
    abstract val storeUrls: StoreUrls
}

fun main(argv: Array<String>) {
    Generator(
        Dc2fConfig(
            contentDirectory = "web/content",
            staticDirectory = "web/static",
            rootContentType = AuthPassWebsite::class,
            theme = WebsiteTheme(),
            urlConfigFromRootContent = { it.config.url }
        )
    ).main(argv)
//    Generator.generate(
//        Generator.Dc2fConfig(
//            contentDirectory = "web/content",
//            staticDirectory = "web/static",
//            setupClass = WebsiteSetup::class
//        )
//    )
}