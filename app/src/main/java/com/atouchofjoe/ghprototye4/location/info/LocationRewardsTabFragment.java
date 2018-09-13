package com.atouchofjoe.ghprototye4.location.info;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.atouchofjoe.ghprototye4.MainActivity;
import com.atouchofjoe.ghprototye4.R;
import com.atouchofjoe.ghprototye4.data.DatabaseDescription;
import com.atouchofjoe.ghprototye4.models.Location;
import com.atouchofjoe.ghprototye4.models.Party;

import java.util.ArrayList;
import java.util.List;

import static com.atouchofjoe.ghprototye4.location.info.LocationInfoActivity.locations;


public class LocationRewardsTabFragment extends LocationTabFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private Location currentLoc;
    private Party currentParty;
    View rootView;
    private int numLoadersFinished = 0;
    private static final int NUM_LOADERS = 8;
    private List<String> locationsUnlocked = new ArrayList<>();
    private List<String> locationsBlocked = new ArrayList<>();
    private List<String> globalAchievementsGained = new ArrayList<>();
    private List<String> globalAchievementsLost = new ArrayList<>();
    private List<String> partyAchievementsGained = new ArrayList<>();
    private List<String> partyAchievementsLost = new ArrayList<>();
    private List<String> addRewards = new ArrayList<>();
    private List<String> addPenalties = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        currentLoc = locations[getArguments().getInt(LocationInfoActivity.ARG_LOCATION_NUMBER)];
        currentParty = MainActivity.currentParty;

        if (currentParty.getLocationCompleted(currentLoc)) {
            rootView = inflater.inflate(R.layout.fragment_scenario_rewards_tab, container, false);
        } else {
            rootView = inflater.inflate(R.layout.content_empty_tab, container, false);

            TextView notCompleteMessage = rootView.findViewById(R.id.emptyMessage);
            notCompleteMessage.setText(R.string.text_not_completed);
        }
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARTY_NAME, currentParty.getName());
        bundle.putString(ARG_LOCATION_NUMBER, "" + getArguments().getInt(LocationInfoActivity.ARG_LOCATION_NUMBER));
        LoaderManager loaderManager = getActivity().getLoaderManager();
        loaderManager.initLoader(LOCATIONS_UNLOCKED_CURSOR_LOADER, bundle, this);
        loaderManager.initLoader(LOCATIONS_BLOCKED_CURSOR_LOADER, bundle, this);
        loaderManager.initLoader(GLOBAL_ACHIEVEMENTS_GAINED_CURSOR_LOADER, bundle, this);
        loaderManager.initLoader(GLOBAL_ACHIEVEMENTS_LOST_CURSOR_LOADER, bundle, this);
        loaderManager.initLoader(PARTY_ACHIEVEMENTS_GAINED_CURSOR_LOADER, bundle, this);
        loaderManager.initLoader(PARTY_ACHIEVEMENTS_LOST_CURSOR_LOADER, bundle, this);
        loaderManager.initLoader(ADD_REWARDS_GAINED_CURSOR_LOADER, bundle, this);
        loaderManager.initLoader(ADD_PENALTIES_LOST_CURSOR_LOADER, bundle, this);

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        numLoadersFinished = 0;
    }

    private void initializeSpinners() {
        // unlocked scenarios
        activateSpinner(locationsUnlocked.size()==0,
        R.id.scenariosUnlockedTableRow,R.id.scenariosUnlockedSpinnerButton,
        R.id.scenariosUnlockedLabelButton,scenariosUnlockedOnClickListener);

        // blocked scenarios
        activateSpinner(locationsBlocked.size() ==0,
        R.id.scenariosBlockedTableRow,R.id.scenariosBlockedSpinnerButton,
        R.id.scenariosBlockedLabelButton,scenariosBlockedOnClickListener);

        // global achievements gained
        activateSpinner(globalAchievementsGained.size() ==0,
        R.id.globalGainedTableRow,R.id.globalGainedSpinnerButton,
        R.id.globalGainedLabelButton,globalGainedOnClickListener);

        // global achievements lost
        activateSpinner(globalAchievementsLost.size() ==0,
        R.id.globalLostTableRow,R.id.globalLostSpinnerButton,
        R.id.globalLostLabelButton,globalLostOnClickListener);

        // party achievements gained
        activateSpinner(partyAchievementsGained.size() ==0,
        R.id.partyGainedTableRow,R.id.partyGainedSpinnerButton,
        R.id.partyGainedLabelButton,partyGainedOnClickListener);

        // party achievements lost
        activateSpinner(partyAchievementsLost.size() ==0,
        R.id.partyLostTableRow,R.id.partyLostSpinnerButton,
        R.id.partyLostLabelButton,partyLostOnClickListener);

        // additional rewards gained
        activateSpinner(addRewards.size() ==0,
        R.id.addRewardsTableRow,R.id.addRewardsSpinnerButton,
        R.id.addRewardsLabelButton,extrasGainedOnClickListener);

        // additional rewards lost
        activateSpinner(addPenalties.size() ==0,
        R.id.extrasLostTableRow,R.id.extrasLostSpinnerButton,
        R.id.extrasLostLabelButton,extrasLostOnClickListener);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        switch(loader.getId()) {
            case LOCATIONS_UNLOCKED_CURSOR_LOADER:
                int unlockedLocIndex = cursor.getColumnIndex(
                        DatabaseDescription.LocationsToBeUnlocked.COLUMN_UNLOCKED_LOCATION);
                while (cursor.moveToNext()) {
                    locationsUnlocked.add(locations[cursor.getInt(unlockedLocIndex)].toString());
                }
                break;
            case LOCATIONS_BLOCKED_CURSOR_LOADER:
                int blockedLocIndex = cursor.getColumnIndex(
                        DatabaseDescription.BlockedLocations.COLUMN_BLOCKED_LOCATION_NUMBER);
                while (cursor.moveToNext()) {
                    locationsBlocked.add(locations[cursor.getInt(blockedLocIndex)].toString());
                }
                break;
            case GLOBAL_ACHIEVEMENTS_GAINED_CURSOR_LOADER:
                int globalAchievementIndex = cursor.getColumnIndex(
                        DatabaseDescription.GlobalAchievementsToBeAwarded.COLUMN_GLOBAL_ACHIEVEMENT);
                while (cursor.moveToNext()) {
                    globalAchievementsGained.add(cursor.getString(globalAchievementIndex));
                }
                break;
            case GLOBAL_ACHIEVEMENTS_LOST_CURSOR_LOADER:
                globalAchievementIndex = cursor.getColumnIndex(
                        DatabaseDescription.GlobalAchievementsToBeRevoked.COLUMN_GLOBAL_ACHIEVEMENT);
                while (cursor.moveToNext()) {
                    globalAchievementsLost.add(cursor.getString(globalAchievementIndex));
                }
                break;
            case PARTY_ACHIEVEMENTS_GAINED_CURSOR_LOADER:
                int partyAchievementIndex = cursor.getColumnIndex(
                        DatabaseDescription.PartyAchievementsToBeAwarded.COLUMN_PARTY_ACHIEVEMENT);
                while (cursor.moveToNext()) {
                    partyAchievementsGained.add(cursor.getString(partyAchievementIndex));
                }
                break;
            case PARTY_ACHIEVEMENTS_LOST_CURSOR_LOADER:
                partyAchievementIndex = cursor.getColumnIndex(
                        DatabaseDescription.PartyAchievementsToBeRevoked.COLUMN_PARTY_ACHIEVEMENT);
                while (cursor.moveToNext()) {
                    partyAchievementsLost.add(cursor.getString(partyAchievementIndex));
                }
                break;
            case ADD_REWARDS_GAINED_CURSOR_LOADER:
                int rewardTypeIndex = cursor.getColumnIndex(
                        DatabaseDescription.AddRewards.COLUMN_REWARD_TYPE);
                int rewardValueIndex = cursor.getColumnIndex(
                        DatabaseDescription.AddRewards.COLUMN_REWARD_VALUE);
                int appliedTypeIndex = cursor.getColumnIndex(
                        DatabaseDescription.AddRewards.COLUMN_REWARD_APPLICATION_TYPE);
                while(cursor.moveToNext()) {
                    StringBuilder string = new StringBuilder("+");
                    string.append("" + cursor.getInt(rewardValueIndex));
                    string.append(" " + cursor.getString(rewardTypeIndex));
                    if(cursor.getString(appliedTypeIndex).equals("each")) {
                        string.append(" each");
                    }
                }
                break;
            case ADD_PENALTIES_LOST_CURSOR_LOADER:
                int penaltyTypeIndex = cursor.getColumnIndex(
                        DatabaseDescription.AddPenalties.COLUMN_PENALTY_TYPE);
                int penaltyValueIndex = cursor.getColumnIndex(
                        DatabaseDescription.AddPenalties.COLUMN_PENALTY_VALUE);
                int penaltyAppliedType = cursor.getColumnIndex(
                        DatabaseDescription.AddPenalties.COLUMN_PENALTY_APPLICATION_TYPE);
                while(cursor.moveToNext()) {
                    StringBuilder string = new StringBuilder("-");
                    string.append("" + cursor.getInt(penaltyValueIndex));
                    string.append(" " + cursor.getString(penaltyTypeIndex));
                    if(cursor.getString(penaltyAppliedType).equals("each")) {
                        string.append(" each");
                    }
                }
                break;
            default:
                throw new IllegalArgumentException();
        }
        numLoadersFinished++;
        if(numLoadersFinished == NUM_LOADERS){
            initializeSpinners();
        }
    }

    private void activateSpinner(boolean makeGone, int tableRowResource,
    int spinnerResource, int labelResource,
    View.OnClickListener listener) {
        if (makeGone) {
            TableRow tableRow = rootView.findViewById(tableRowResource);
            tableRow.setVisibility(View.GONE);
        } else {
            AppCompatImageButton spinner = rootView.findViewById(spinnerResource);
            spinner.setOnClickListener(listener);

            Button label = rootView.findViewById(labelResource);
            label.setOnClickListener(listener);
        }
    }

        private void updateSpinner(AppCompatImageButton spinner, RecyclerView rView, List<String> list) {
            LinearLayout.LayoutParams params= ((LinearLayout.LayoutParams) rView.getLayoutParams());
            if (params.weight == 0) {
                params.weight = list.size();
                spinner.setImageResource(R.drawable.ic_expand_more_24dp);
            } else {
                params.weight = 0;
                spinner.setImageResource(R.drawable.ic_chevron_right__24dp);
            }
            rView.setLayoutParams(params);
            rootView.invalidate();
        }

    private View.OnClickListener scenariosUnlockedOnClickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatImageButton spinner;
                    Button label;
                    RecyclerView rView;
                    if (view.getClass() == AppCompatImageButton.class) {
                        spinner = (AppCompatImageButton) view;
                        label = view.getRootView().findViewById(R.id.scenariosUnlockedLabelButton);
                    } else {
                        spinner = view.getRootView().findViewById(R.id.scenariosUnlockedSpinnerButton);
                        label = (Button) view;
                    }
                    rView = label.getRootView()
                            .findViewById(R.id.scenariosUnlockedRecyclerView);
                    setupRecyclerView(rView, locationsUnlocked);
                    updateSpinner(spinner, rView, locationsUnlocked);
                }
            };



    private View.OnClickListener scenariosBlockedOnClickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatImageButton spinner;
                    Button label;
                    RecyclerView rView;
                    if (view.getClass() == AppCompatImageButton.class) {
                        spinner = (AppCompatImageButton) view;
                        label = view.getRootView().findViewById(R.id.scenariosBlockedLabelButton);
                    } else {
                        spinner = view.getRootView().findViewById(R.id.scenariosBlockedSpinnerButton);
                        label = (Button) view;
                    }
                    rView = label.getRootView()
                            .findViewById(R.id.scenariosBlockedRecyclerView);
                    setupRecyclerView(rView, locationsBlocked);
                    updateSpinner(spinner, rView, locationsBlocked);
                }
            };

    private View.OnClickListener globalGainedOnClickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatImageButton spinner;
                    Button label;
                    RecyclerView rView;
                    if (view.getClass() == AppCompatImageButton.class) {
                        spinner = (AppCompatImageButton) view;
                        label = view.getRootView().findViewById(R.id.globalGainedLabelButton);
                    } else {
                        spinner = view.getRootView().findViewById(R.id.globalGainedSpinnerButton);
                        label = (Button) view;
                    }
                    rView = label.getRootView()
                            .findViewById(R.id.globalGainedRecyclerView);
                    setupRecyclerView(rView, globalAchievementsGained);
                    updateSpinner(spinner, rView, globalAchievementsGained);
                }
            };

    private View.OnClickListener globalLostOnClickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatImageButton spinner;
                    Button label;
                    RecyclerView rView;
                    if (view.getClass() == AppCompatImageButton.class){
                        spinner = (AppCompatImageButton) view;
                        label = view.getRootView().findViewById(R.id.globalLostLabelButton);
                    }
                    else
                    {
                        spinner = view.getRootView().findViewById(R.id.globalLostSpinnerButton);
                        label = (Button) view;
                    }
                    rView = label.getRootView().findViewById(R.id.globalLostRecyclerView);
                    setupRecyclerView(rView, globalAchievementsLost);
                    updateSpinner(spinner, rView, globalAchievementsLost);
                }
            };

    private  View.OnClickListener partyGainedOnClickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatImageButton spinner;
                    Button label;
                    RecyclerView rView;
                    if (view.getClass() == AppCompatImageButton.class){
                        spinner = (AppCompatImageButton) view;
                        label = view.getRootView().findViewById(R.id.partyGainedLabelButton);
                    }
                    else
                    {
                        spinner = view.getRootView().findViewById(R.id.partyGainedSpinnerButton);
                        label = (Button) view;
                    }
                    rView = label.getRootView()
                            .findViewById(R.id.partyGainedRecyclerView);
                    setupRecyclerView(rView, partyAchievementsGained);
                    updateSpinner(spinner, rView, partyAchievementsGained);
                }
            };

    private View.OnClickListener partyLostOnClickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatImageButton spinner;
                    Button label;
                    RecyclerView rView;
                    if (view.getClass() == AppCompatImageButton.class){
                        spinner = (AppCompatImageButton) view;
                        label = view.getRootView().findViewById(R.id.partyLostLabelButton);
                    }
                    else
                    {
                        spinner = view.getRootView().findViewById(R.id.partyLostSpinnerButton);
                        label = (Button) view;
                    }
                    rView = label.getRootView()
                            .findViewById(R.id.partyLostRecyclerView);
                    setupRecyclerView(rView, globalAchievementsLost);
                    updateSpinner(spinner, rView, globalAchievementsLost);
                }
            };

    private View.OnClickListener extrasGainedOnClickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatImageButton spinner;
                    Button label;
                    RecyclerView rView;
                    if (view.getClass() == AppCompatImageButton.class){
                        spinner = (AppCompatImageButton) view;
                        label = view.getRootView().findViewById(R.id.addRewardsLabelButton);
                    }
                    else
                    {
                        spinner = view.getRootView().findViewById(R.id.addRewardsLabelButton);
                        label = (Button) view;
                    }
                    rView = label.getRootView()
                            .findViewById(R.id.extrasGainedRecyclerView);
                    setupRecyclerView(rView, addRewards);
                    updateSpinner(spinner, rView, addRewards);
                }
            };

    private View.OnClickListener extrasLostOnClickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatImageButton spinner;
                    Button label;
                    RecyclerView rView;
                    if (view.getClass() == AppCompatImageButton.class){
                        spinner = (AppCompatImageButton) view;
                        label = view.getRootView().findViewById(R.id.extrasLostLabelButton);
                    }
                    else
                    {
                        spinner = view.getRootView().findViewById(R.id.extrasLostSpinnerButton);
                        label = (Button) view;
                    }
                    rView = label.getRootView()
                            .findViewById(R.id.extrasLostRecyclerView);
                    setupRecyclerView(rView, addPenalties);
                    updateSpinner(spinner, rView, addPenalties);
                }
            };

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, List<String> data) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(getActivity(), data));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<String> mValues;
        private LayoutInflater mInflater;


        private SimpleItemRecyclerViewAdapter(Context context, List<String> data){

            this.mInflater = LayoutInflater.from(context);
            mValues = data;
        }

        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                //                   Context context = view.getContext();
                //                 Intent intent = new Intent(context, DataPointDetailActivity.class);
                //              intent.putExtra(DataPointDetailFragment.ARG_SCENARIO_NUMBER, scenario.sNumber);
            }
        };

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater
                    .inflate(R.layout.content_reward_detail, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.button.setText(mValues.get(position));

        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final Button button;

            ViewHolder(View view) {
                super(view);
                button = view.findViewById(R.id.detailButton);
                button.setOnClickListener(mOnClickListener);
            }
        }
    }
}
