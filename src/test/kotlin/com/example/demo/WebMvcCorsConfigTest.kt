package com.example.demo

import com.example.web.CorsConfig
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldNotBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(
    classes = [TestApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
)
@AutoConfigureMockMvc
class WebMvcCorsConfigTest @Autowired constructor(
    private val applicationContext: ApplicationContext,
    private val mockMvc: MockMvc
) : FreeSpec({

    "WebMvcCorsConfig 빈 로드 테스트" - {
        "컨텍스트에 WebMvcCorsConfig 빈이 존재해야 한다" {
            val corsConfig = applicationContext.getBean(CorsConfig::class.java)
            corsConfig shouldNotBe null
        }
    }

    "허용된 URL, Methods, Headers 목록으로" - {
        val origin = "http://localhost:3000"
        val method = "GET"
        val endPoint = "/"
        val request = options(endPoint)
            .header("Origin", origin)
            .header("Access-Control-Request-Method", method)

        "Preflight 요청을 보내면" - {
            val result = mockMvc.perform(request)

            "✅ 정상 상태 코드로 응답한다. (2XX)" {
                result.andExpect { status().is2xxSuccessful }
            }

            "✅ 입력한 origin이 허용 목록에 포함된다. (Access-Control-Allow-Origin 헤더)" {
                result.andExpect {
                    header().string("Access-Control-Allow-Origin", origin)
                }
            }

            "✅ 입력한 Method가 허용 목록에 포함된다. (Access-Control-Allow-Methods 헤더)" {
                result.andExpect {
                    header().string("Access-Control-Allow-Methods", method)
                }
            }
        }
    }

    "잘못된 URL로" - {
        val notAllowedOrigin = "http://localhost:3001"
        val method = "GET"
        val endPoint = "/"
        val request = options(endPoint)
            .header("Origin", notAllowedOrigin)
            .header("Access-Control-Request-Method", method)

        "Preflight 요청을 보내면" - {
            val result = mockMvc.perform(request)

            "✋ 400번대 클라이언트 오류 상태 코드로 응답한다. (4XX)" {
                result.andExpect { status().is4xxClientError }
            }

            "✋ 입력한 origin이 허용 목록에 포함되지 않는다. (Access-Control-Allow-Origin 헤더)" {
                result.andExpect {
                    header().doesNotExist("Access-Control-Allow-Origin")
                }
            }

            "✋ 헤더에 허용 Method 목록이 포함되지 않는다. (Access-Control-Allow-Methods 헤더)" {
                result.andExpect {
                    header().doesNotExist("Access-Control-Allow-Methods")
                }
            }
        }
    }
})