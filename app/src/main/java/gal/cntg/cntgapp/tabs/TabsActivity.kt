package gal.cntg.cntgapp.tabs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import gal.cntg.cntgapp.R

class TabsActivity : AppCompatActivity(), TabLayoutMediator.TabConfigurationStrategy {

    lateinit var viewPager2: ViewPager2
    lateinit var tabLayout: TabLayout
    lateinit var adapterTabs: AdapterTabs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabs)

        this.viewPager2 = findViewById(R.id.vpt)
        this.tabLayout = findViewById(R.id.tablayout)
        this.adapterTabs = AdapterTabs(this)

        this.viewPager2.adapter = this.adapterTabs
        // asociar el tablayout el viewpager. Implementa mñetodo onConfigureTab
        TabLayoutMediator(tabLayout, viewPager2, this).attach()

    }

    // cada vez qie está activo el tab, se invoca a este método para poder modificar su apariencia.
    override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
        tab.text = "VISTA ${position + 1}"
    }
}