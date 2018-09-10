package com.atouchofjoe.ghprototye4.location.info;

import android.support.v4.app.Fragment;

import com.atouchofjoe.ghprototye4.Dummy.DummyContent;
import com.atouchofjoe.ghprototye4.MainActivity;
import com.atouchofjoe.ghprototye4.models.Location;

import java.util.List;

public class LocationTabFragment extends Fragment {

    protected Location[] dummyScenarios = MainActivity.content.getLocations();
}
