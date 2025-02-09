package com.example.demo

import com.example.web.CorsConfig
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldNotBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext

@SpringBootTest(classes = [TestApplication::class])
class WebMvcCorsConfigTest @Autowired constructor(
    private val applicationContext: ApplicationContext
) : FreeSpec({

    "WebMvcCorsConfig 빈 로드 테스트" - {
        "컨텍스트에 WebMvcCorsConfig 빈이 존재해야 한다" {
            val corsConfig = applicationContext.getBean(CorsConfig::class.java)
            corsConfig shouldNotBe null
        }
    }
})