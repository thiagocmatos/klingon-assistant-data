/*
 * Copyright (C) 2013 De'vID jonpIn (David Yonge-Mallo)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tlhInganHol.android.klingonassistant;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class BaseActivity extends SherlockActivity implements SlideMenuAdapter.MenuListener {
    private static final String TAG = "BaseActivity";

    // This must uniquely identify the {boQwI'} entry.
    protected static final String QUERY_FOR_ABOUT                = "boQwI':n";

    private static final String STATE_ACTIVE_POSITION =
            "org.tlhInganHol.android.klingonassistant.activePosition";

    // Help pages.
    private static final String QUERY_FOR_PRONUNCIATION          = "QIch:n";
    private static final String QUERY_FOR_PREFIXES               = "moHaq:n";
    private static final String QUERY_FOR_NOUN_SUFFIXES          = "DIp:n";
    private static final String QUERY_FOR_VERB_SUFFIXES          = "wot:n";

    // Classes of phrases.
    private static final String QUERY_FOR_EMPIRE_UNION_DAY       = "*:sen:eu";
    private static final String QUERY_FOR_IDIOMS                 = "*:sen:idiom";
    private static final String QUERY_FOR_CURSE_WARFARE          = "*:sen:mv";
    private static final String QUERY_FOR_NENTAY                 = "*:sen:nt";
    private static final String QUERY_FOR_PROVERBS               = "*:sen:prov";
    private static final String QUERY_FOR_QI_LOP                 = "*:sen:Ql";
    private static final String QUERY_FOR_REJECTION              = "*:sen:rej";
    private static final String QUERY_FOR_REPLACEMENT_PROVERBS   = "*:sen:rp";
    private static final String QUERY_FOR_SECRECY_PROVERBS       = "*:sen:sp";
    private static final String QUERY_FOR_TOASTS                 = "*:sen:toast";
    private static final String QUERY_FOR_LYRICS                 = "*:sen:lyr";
    private static final String QUERY_FOR_BEGINNERS_CONVERSATION = "*:sen:bc";
    private static final String QUERY_FOR_JOKES                  = "*:sen:joke";


    private MenuDrawer mDrawer;

    protected SlideMenuAdapter mAdapter;
    protected ListView mList;

    private int mActivePosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mActivePosition = savedInstanceState.getInt(STATE_ACTIVE_POSITION);
        }

        getSupportActionBar();

        // If the device is in landscape orientation and the screen size is large (or bigger), then
        // make the slide-out menu static. Otherwise, hide it by default.
        Configuration config = getResources().getConfiguration();
        MenuDrawer.Type drawerType = MenuDrawer.Type.BEHIND;
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE &&
            (config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE) {
            drawerType = MenuDrawer.Type.STATIC;
        }
        mDrawer = MenuDrawer.attach(this, drawerType, Position.LEFT, MenuDrawer.MENU_DRAG_CONTENT);

        List<Object> items = new ArrayList<Object>();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if (sharedPrefs.getBoolean(Preferences.KEY_KLINGON_UI_CHECKBOX_PREFERENCE, /* default */false)) {
            items.add(new SlideMenuCategory(R.string.menu_reference_tlh));
                items.add(new SlideMenuItem(R.string.menu_pronunciation_tlh, R.id.pronunciation, 0));
                items.add(new SlideMenuItem(R.string.menu_prefixes_tlh, R.id.prefixes, 0));
                items.add(new SlideMenuItem(R.string.menu_noun_suffixes_tlh, R.id.noun_suffixes, 0));
                items.add(new SlideMenuItem(R.string.menu_verb_suffixes_tlh, R.id.verb_suffixes, 0));
            items.add(new SlideMenuCategory(R.string.menu_phrases_tlh));
                items.add(new SlideMenuItem(R.string.beginners_conversation_tlh, R.id.beginners_conversation, 0));
                items.add(new SlideMenuItem(R.string.jokes_tlh, R.id.jokes, 0));
                items.add(new SlideMenuItem(R.string.nentay_tlh, R.id.nentay, 0));
                items.add(new SlideMenuItem(R.string.military_celebration_tlh, R.id.military_celebration, 0));
                items.add(new SlideMenuItem(R.string.toasts_tlh, R.id.toasts, 0));
                items.add(new SlideMenuItem(R.string.lyrics_tlh, R.id.lyrics, 0));
                items.add(new SlideMenuItem(R.string.curse_warfare_tlh, R.id.curse_warfare, 0));
                items.add(new SlideMenuItem(R.string.replacement_proverbs_tlh, R.id.replacement_proverbs, 0));
                items.add(new SlideMenuItem(R.string.secrecy_proverbs_tlh, R.id.secrecy_proverbs, 0));
                items.add(new SlideMenuItem(R.string.empire_union_day_tlh, R.id.empire_union_day, 0));
                items.add(new SlideMenuItem(R.string.rejection_tlh, R.id.rejection, 0));
            items.add(new SlideMenuCategory(R.string.menu_social_tlh));
                items.add(new SlideMenuItem(R.string.menu_gplus_tlh, R.id.gplus, 0));
                items.add(new SlideMenuItem(R.string.menu_facebook_tlh, R.id.facebook, 0));
                items.add(new SlideMenuItem(R.string.menu_kag_tlh, R.id.kag, 0));
                items.add(new SlideMenuItem(R.string.menu_kidc_tlh, R.id.kidc, 0));
        } else {
            items.add(new SlideMenuCategory(R.string.menu_reference));
                items.add(new SlideMenuItem(R.string.menu_pronunciation, R.id.pronunciation, 0));
                items.add(new SlideMenuItem(R.string.menu_prefixes, R.id.prefixes, 0));
                items.add(new SlideMenuItem(R.string.menu_noun_suffixes, R.id.noun_suffixes, 0));
                items.add(new SlideMenuItem(R.string.menu_verb_suffixes, R.id.verb_suffixes, 0));
            items.add(new SlideMenuCategory(R.string.menu_phrases));
                items.add(new SlideMenuItem(R.string.beginners_conversation, R.id.beginners_conversation, 0));
                items.add(new SlideMenuItem(R.string.jokes, R.id.jokes, 0));
                items.add(new SlideMenuItem(R.string.nentay, R.id.nentay, 0));
                items.add(new SlideMenuItem(R.string.military_celebration, R.id.military_celebration, 0));
                items.add(new SlideMenuItem(R.string.toasts, R.id.toasts, 0));
                items.add(new SlideMenuItem(R.string.lyrics, R.id.lyrics, 0));
                items.add(new SlideMenuItem(R.string.curse_warfare, R.id.curse_warfare, 0));
                items.add(new SlideMenuItem(R.string.replacement_proverbs, R.id.replacement_proverbs, 0));
                items.add(new SlideMenuItem(R.string.secrecy_proverbs, R.id.secrecy_proverbs, 0));
                items.add(new SlideMenuItem(R.string.empire_union_day, R.id.empire_union_day, 0));
                items.add(new SlideMenuItem(R.string.rejection, R.id.rejection, 0));
                // Not all general proverbs are properly tagged yet.
                // Too many idioms; also no known Klingon term for "idiom".
            items.add(new SlideMenuCategory(R.string.menu_social));
                items.add(new SlideMenuItem(R.string.menu_gplus, R.id.gplus, 0));
                items.add(new SlideMenuItem(R.string.menu_facebook, R.id.facebook, 0));
                items.add(new SlideMenuItem(R.string.menu_kag, R.id.kag, 0));
                items.add(new SlideMenuItem(R.string.menu_kidc, R.id.kidc, 0));
        }
        mList = new ListView(this);

        mAdapter = new SlideMenuAdapter(this, items);
        mAdapter.setListener(this);
        mAdapter.setActivePosition(mActivePosition);

        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(mItemClickListener);

        mDrawer.setMenuView(mList);

        // Allow the menu to slide out when any part of the screen is dragged.
        mDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);

        // The drawable that replaces the up indicator in the action bar.
        mDrawer.setSlideDrawable(R.drawable.ic_drawer);
        // Whether the previous drawable should be shown.
        mDrawer.setDrawerIndicatorEnabled(true);
    }

    // Set the content view for the menu drawer.
    protected void setDrawerContentView(int layoutResId) {
        mDrawer.setContentView(layoutResId);
    }

    protected void onSlideMenuItemClicked(int position, SlideMenuItem item) {
        mDrawer.closeMenu();

        switch (item.getItemId()) {
        case R.id.pronunciation:
          // Show "Pronunciation" screen.
          displayHelp(QUERY_FOR_PRONUNCIATION);
          break;
        case R.id.prefixes:
          // Show "Prefixes" screen.
          displayHelp(QUERY_FOR_PREFIXES);
          break;
        case R.id.noun_suffixes:
          // Show "Noun Suffixes" screen.
          displayHelp(QUERY_FOR_NOUN_SUFFIXES);
          break;
        case R.id.verb_suffixes:
          // Show "Verb Suffixes" screen.
          displayHelp(QUERY_FOR_VERB_SUFFIXES);
          break;

        // Handle social networks.
        case R.id.gplus:
          // Launch Google+ Klingon speakers community.
          launchExternal("https://plus.google.com/communities/108380135139365833546");
          break;

        case R.id.facebook:
          // Launch Facebook "Learn Klingon" group.
          launchExternal("https://www.facebook.com/groups/LearnKlingon/");
          break;

        case R.id.kag:
          // Launch KAG Communications.
          launchExternal("http://comms.kag.org/viewforum.php?f=14");
          break;

        case R.id.kidc:
          // Launch KIDC's Klingon Imperial Forums.
          launchExternal("http://www.klingon.org/smboard/index.php?board=6.0");
          break;

        // Handle classes of phrases.
        case R.id.empire_union_day:
          displayHelp(QUERY_FOR_EMPIRE_UNION_DAY);
          break;
          /*
           * case R.id.idioms: displayHelp(QUERY_FOR_IDIOMS); return true;
           */
        case R.id.curse_warfare:
          displayHelp(QUERY_FOR_CURSE_WARFARE);
          break;
        case R.id.nentay:
          displayHelp(QUERY_FOR_NENTAY);
          break;
          /*
           * case R.id.proverbs: displayHelp(QUERY_FOR_PROVERBS); return true;
           */
        case R.id.military_celebration:
          displayHelp(QUERY_FOR_QI_LOP);
          break;
        case R.id.rejection:
          displayHelp(QUERY_FOR_REJECTION);
          break;
        case R.id.replacement_proverbs:
          displayHelp(QUERY_FOR_REPLACEMENT_PROVERBS);
          break;
        case R.id.secrecy_proverbs:
          displayHelp(QUERY_FOR_SECRECY_PROVERBS);
          break;
        case R.id.toasts:
          displayHelp(QUERY_FOR_TOASTS);
          break;
        case R.id.lyrics:
          displayHelp(QUERY_FOR_LYRICS);
          break;
        case R.id.beginners_conversation:
          displayHelp(QUERY_FOR_BEGINNERS_CONVERSATION);
          break;
        case R.id.jokes:
          displayHelp(QUERY_FOR_JOKES);
          break;

          // Lists.
          // TODO: Handle lists here.

        default:
        }

    }

    // Private method to launch an external app or web site.
    private void launchExternal(String externalUrl) {
      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      intent.setData(Uri.parse(externalUrl));
      startActivity(intent);
    }

    // Protected method to display the "help" entries.
    protected void displayHelp(String helpQuery) {
      Intent intent = new Intent(this, KlingonAssistant.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      intent.setAction(Intent.ACTION_SEARCH);
      intent.putExtra(SearchManager.QUERY, helpQuery);

      startActivity(intent);
    }

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mActivePosition = position;
            mDrawer.setActiveView(view, position);
            mAdapter.setActivePosition(position);
            onSlideMenuItemClicked(position, (SlideMenuItem) mAdapter.getItem(position));
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_ACTIVE_POSITION, mActivePosition);
    }

    @Override
    public void onActiveViewChanged(View v) {
        mDrawer.setActiveView(v, mActivePosition);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      case R.id.search:
        onSearchRequested();
        return true;
      case android.R.id.home:
        mDrawer.toggleMenu();
        break;
      case R.id.about:
        // Show "About" screen.
        displayHelp(QUERY_FOR_ABOUT);
        return true;
      case R.id.preferences:
        // Show "Preferences" screen.
        startActivity(new Intent(this, Preferences.class));
        return true;
      default:
      }

      return super.onOptionsItemSelected(item);
    }

    // Collapse slide-out menu if "Back" key is pressed and it's open.
    @Override
    public void onBackPressed() {
        final int drawerState = mDrawer.getDrawerState();
        if (drawerState == MenuDrawer.STATE_OPEN || drawerState == MenuDrawer.STATE_OPENING) {
            mDrawer.closeMenu();
            return;
        }

        super.onBackPressed();
    }
}
