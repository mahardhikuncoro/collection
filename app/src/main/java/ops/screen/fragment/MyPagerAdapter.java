package ops.screen.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import base.sqlite.NewsModel;

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> pages;
    public MyPagerAdapter(FragmentManager fm, ArrayList<NewsModel> list) {
        super(fm);
        notifyDataSetChanged();
        initPages(list);
    }

    /*@Override
    public Fragment getItem(int pos) {
        switch(pos) {
            case 0:
                return SimpleFragment.newInstance("Page 1");
            case 1:
                return SimpleFragment.newInstance("Page 2");
            case 2:
                return SimpleFragment.newInstance("Page 3");
            case 3:
                return SimpleFragment.newInstance("Page 4");
            default:
                return SimpleFragment.newInstance("Page 99");
        }
    }*/

    private void initPages(ArrayList id) {
        pages = new ArrayList<>();
        Log.e("HAHAHAHHA "," WOII id" + id);
        addPage(id);
    }

    /**
     * Add new BookListFragment to the ViewPager.
     *
     * @param categoryId
     *            - the category id
     */
    public void addPage(ArrayList<NewsModel> categoryId) {
        for(int i = 0; i<categoryId.size() ;i++)
            pages.add(SimpleFragment.newInstance(categoryId.get(i).getNewsDesc()));
    }

    @Override
    public Fragment getItem(int position) {
        Log.e("WOISSS "," : " + position);
        return pages.get(position);
    }

    @Override
    public int getCount() {
        return pages.size();
    }

}

class BookOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
    private int currentPage;

    @Override
    public void onPageSelected(int position) {
        // current page from the actual position
        currentPage = position;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}