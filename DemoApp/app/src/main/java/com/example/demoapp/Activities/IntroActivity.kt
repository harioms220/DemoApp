package com.example.demoapp.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.view.View
import com.example.demoapp.R
import com.example.viewpager.Fragments.FragmentA
import com.example.viewpager.Fragments.FragmentB
import com.example.viewpager.Fragments.FragmentC

import kotlinx.android.synthetic.main.activity_intro.*

// Activity which will be launched on the first start up of the app.


class IntroActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        viewPager.adapter = MypagerAdapter(supportFragmentManager)
        spring_dots_indicator.setViewPager(viewPager)

        create_account.setOnClickListener{
            val intent = Intent(baseContext , Account_chooser_activity::class.java)
            startActivity(intent)
        }

        login.setOnClickListener {
            val intent = Intent(baseContext , Account_chooser_activity::class.java)
            startActivity(intent)
        }
    }
}



class MypagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager){
    override fun getItem(p0: Int): Fragment {
        return when(p0){
            0 -> FragmentA()
            1 -> FragmentB()
            else -> FragmentC()
        }
    }

    override fun getCount(): Int = 3


//    override fun getPageTitle(position: Int): CharSequence? {
//        return when(position){
//            0 -> "A"
//            1 -> "B"
//            2 -> "C"
//            else -> "D"
//        }
//    }

}
