package com.tencent.virtualman_android_client_demo

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.tencent.ivhfilamentsdk.Ivh
import com.tencent.ivhfilamentsdk.IvhParams
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class MainActivity : AppCompatActivity() {
    private lateinit var mIvh: Ivh
    private var mIvhParams = IvhParams()
    private val TAG = "ivh-demo"
    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        mIvh = findViewById(R.id.ivh)
        // init事件要提前
        mIvh.on("init") { type, message ->
            Log.i(TAG, "$type, $message")
        }
        mIvh.on("error") { type, message ->
            Log.e(TAG, "$type, $message")
        }
        mIvh.on("debug") { type, message ->
            Log.v(TAG, "$type, $message")
        }
        // 此处填写自己数智人的glb模型path, 模型配置path, appkey, accessToken和virtualmanProjectId
        mIvhParams.modelPath = "xx.glb"
        mIvhParams.configPath = "xx.json"
        mIvhParams.appkey = "xxxx"
        mIvhParams.accesstoken = "xxxx"
        mIvhParams.virtualmanProjectId = "xxxx"
        mIvhParams.isIntellAction = false
        mIvh.init(mIvhParams, object : WebSocketListener(){
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                // 处理连接打开事件
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                // 处理接收到的消息
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
                // 处理连接正在关闭事件
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                // 处理连接失败事件
            }
        })
        // ivh事件
        mIvh.on("action") { type, message ->
            Log.i(TAG, "$type, $message")
        }
        mIvh.on("mouth") { type, message  ->
            Log.i(TAG, "$type, $message")
        }
        mIvh.on("sentence") { type, message ->
            Log.i(TAG, "$type, $message")
        }
        // 发送文本
        val sendButton = findViewById<Button>(R.id.btn_send)
        val input = findViewById<EditText>(R.id.text)
        sendButton.setOnClickListener {
            val inputText = input.text.toString()
            if (inputText.isNotEmpty()) {
                mIvh.enableAudio()
                val option = mutableMapOf("driverType" to "text")
                mIvh.play(inputText, option)
                input.setText("")
                // 收起键盘
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mIvh.resume()
    }

    override fun onPause() {
        super.onPause()
        mIvh.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mIvh.destroy()
    }
}