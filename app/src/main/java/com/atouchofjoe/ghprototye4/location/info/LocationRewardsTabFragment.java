package com.atouchofjoe.ghprototye4.location.info;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import java.util.Iterator;
import java.util.List;

import static com.atouchofjoe.ghprototye4.location.info.LocationInfoActivity.locations;


public class LocationRewardsTabFragment extends LocationTabFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private Location currentLoc;
    private Party currentParty;
    View rootView;
    private int numLoadersFinished;
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
        numLoadersFinished = 0;
        currentLoc = locations[getArguments().getInt(LocationInfoActivity.ARG_LOCATION_NUMBER)];
        currentParty = MainActivity.currentParty;

        if (!currentParty.getLocationCompleted(currentLoc)) {
            rootView = inflater.inflate(R.layout.content_empty_tab, container, false);

            TextView notCompleteMessage = rootView.findViewById(R.id.emptyMessage);
            notCompleteMessage.setText(R.string.text_not_completed);

            return rootView;
        }

        // if the currentLoc has been completed by the party
        rootView = inflater.inflate(R.layout.fragment_scenario_rewards_tab, container, false);

        Bundle bundle = new Bundle();
        bundle.putString(LocationInfoActivity.ARG_PARTY_NAME, currentParty.getName());
        bundle.putString(LocationInfoActivity.ARG_LOCATION_NUMBER, "" + currentLoc.getNumber());

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
                locationsUnlocked.clear();
                int unlockedLocNumberIndex = cursor.getColumnIndex(
                        DatabaseDescription.LocationsToBeUnlocked.COLUMN_UNLOCKED_LOCATION_NUMBER);
                int unlockedLocNameIndex = cursor.getColumnIndex(
                        DatabaseDescription.LocationsToBeUnlocked.COLUMN_UNLOCKED_LOCATION_NAME);
                while (cursor.moveToNext()) {
                    String unlockedLocStr = "#" + cursor.getInt(unlockedLocNumberIndex) + " " +
                            cursor.getString(unlockedLocNameIndex);
                    locationsUnlocked.add(unlockedLocStr);
                }
                break;
            case LOCATIONS_BLOCKED_CURSOR_LOADER:
                locationsBlocked.clear();

                int blockedLocNumberIndex = cursor.getColumnIndex(
                        DatabaseDescription.LocationsToBeBlocked.COLUMN_BLOCKED_LOCATION_NUMBER);
                int blockedLocNameIndex = cursor.getColumnIndex(
                        DatabaseDescription.LocationsToBeBlocked.COLUMN_BLOCKED_LOCATION_NAME);
                while (cursor.moveToNext()) {
                    String blockedLocStr = "#" + cursor.getInt(blockedLocNumberIndex) + " " +
                            cursor.getString(blockedLocNameIndex);
                    locationsBlocked.add(blockedLocStr);
                }
                break;
            case GLOBAL_ACHIEVEMENTS_GAINED_CURSOR_LOADER:
                globalAchievementsGained.clear();
                int globalAchievementIndex = cursor.getColumnIndex(
                        DatabaseDescription.GlobalAchievementsToBeAwarded.COLUMN_GLOBAL_ACHIEVEMENT);
                while (cursor.moveToNext()) {
                    globalAchievementsGained.add(cursor.getString(globalAchievementIndex));
                }
                break;
            case GLOBAL_ACHIEVEMENTS_LOST_CURSOR_LOADER:
                globalAchievementsLost.clear();
                globalAchievementIndex = cursor.getColumnIndex(
                        DatabaseDescription.GlobalAchievementsToBeRevoked.COLUMN_GLOBAL_ACHIEVEMENT);
                while (cursor.moveToNext()) {
                    globalAchievementsLost.add(cursor.getString(globalAchievementIndex));
                }
                break;
            case PARTY_ACHIEVEMENTS_GAINED_CURSOR_LOADER:
                partyAchievementsGained.clear();
                int partyAchievementIndex = cursor.getColumnIndex(
                        DatabaseDescription.PartyAchievementsToBeAwarded.COLUMN_PARTY_ACHIEVEMENT);
                while (cursor.moveToNext()) {
                    partyAchievementsGained.add(cursor.getString(partyAchievementIndex));
                }
                break;
            case PARTY_ACHIEVEMENTS_LOST_CURSOR_LOADER:
                partyAchievementsLost.clear();
                partyAchievementIndex = cursor.getColumnIndex(
                        DatabaseDescription.PartyAchievementsToBeRevoked.COLUMN_PARTY_ACHIEVEMENT);
                while (cursor.moveToNext()) {
                    partyAchievementsLost.add(cursor.getString(partyAchievementIndex));
                }
                break;
            case ADD_REWARDS_GAINED_CURSOR_LOADER:
                addRewards.clear();
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
                // TODO where is this string variable used?
                break;
            case ADD_PENALTIES_LOST_CURSOR_LOADER:
                addPenalties.clear();
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
                // TODO where is this string variable used?
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
                    setupRecyclerView(rView, locationsUnlocked, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String locationString = ((Button) view).getText().toString();
                            TextUtils.SimpleStringSplitter splitter = new TextUtils.SimpleStringSplitter(' ');
                            splitter.setString(locationString);
                            Iterator<String> iterator = splitter.iterator();
                            String numberStr = iterator.next();
                            numberStr = numberStr.substring(1);
                            int locNumber = Integer.parseInt(numberStr);

                            Intent intent = new Intent(getActivity(), LocationInfoActivity.class);
                            intent.putExtra(LocationInfoActivity.ARG_LOCATION_NUMBER, locNumber);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });
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
                    setupRecyclerView(rView, locationsBlocked, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
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
                    setupRecyclerView(rView, globalAchievementsGained, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
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
                    setupRecyclerView(rView, globalAchievementsLost, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
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
                    setupRecyclerView(rView, partyAchievementsGained, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
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
                    setupRecyclerView(rView, globalAchievementsLost, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
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
                    rView = label.getRootView().findViewById(R.id.extrasGainedRecyclerView);
                    setupRecyclerView(rView, addRewards, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
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
                    setupRecyclerView(rView, addPenalties, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                    updateSpinner(spinner, rView, addPenalties);
                }
            };

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, List<String> data, View.OnClickListener onClickListener) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(getActivity(), data, onClickListener));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<String> mValues;
        private LayoutInflater mInflater;
        private View.OnClickListener mOnClickListener;


        private SimpleItemRecyclerViewAdapter(Context context, List<String> data, View.OnClickListener onClickListener){

            this.mInflater = LayoutInflater.from(context);
            mValues = data;
            mOnClickListener = onClickListener;
        }

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
