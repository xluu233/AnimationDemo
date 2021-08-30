package com.example.animationdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.animationdemo.databinding.ActivityMainBinding
import com.hi.dhl.binding.viewbind

import android.content.Intent
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair





class MainActivity : AppCompatActivity() {

    private val TAG = javaClass.name
    private val binding :ActivityMainBinding by viewbind()


    private var anim_enter = 0
    private var anim_out = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.sys_default -> {
                    anim_enter = 0
                    anim_out = 0
                }
                R.id.anim_alpha -> {
                    anim_enter = R.anim.alpha_in
                    anim_out = R.anim.alpha_out
                }
                R.id.anim_slide -> {
                    anim_enter = R.anim.slide_in_left
                    anim_out = R.anim.slide_out_right
                }
                R.id.anim_scale -> {
                    anim_enter = R.anim.scale_in
                    anim_out = R.anim.scale_out
                }
            }
        }
        binding.button.setOnClickListener {
            val intent = Intent(this,MainActivity2::class.java)
            startActivity(intent)
            //进入动画(for MainActivity2)和退出动画(for MainActivity)
            overridePendingTransition(anim_enter,anim_out)
        }

        binding.button2.setOnClickListener {
            //val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this,binding.imageview,"shareElement").toBundle()
            val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this,binding.searchEdit,"shareSearch").toBundle()

            val intent = Intent(this,MainActivity3::class.java)
            startActivity(intent, bundle)


            //多个共享元素
//            val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this, Pair(binding.imageview,"shareElement"),
//                Pair(binding.searchEdit,"shareSearch")
//            ).toBundle()
//            val intent = Intent(this,MainActivity3::class.java)
//            startActivity(intent, bundle)

        }

        binding.button3.setOnClickListener {
            val activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(
                this,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            val intent = Intent(this,MainActivity3::class.java)
            startActivity(intent, activityOptionsCompat.toBundle())

        }

        binding.button4.setOnClickListener {
            val activityOptionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(
                binding.imageview,
                binding.imageview.x.toInt(),
                binding.imageview.y.toInt(),
                0,
                0
            )
            val intent = Intent(this,MainActivity3::class.java)
            startActivity(intent, activityOptionsCompat.toBundle())
        }



    }


    override fun finish() {
        super.finish()
    }

}