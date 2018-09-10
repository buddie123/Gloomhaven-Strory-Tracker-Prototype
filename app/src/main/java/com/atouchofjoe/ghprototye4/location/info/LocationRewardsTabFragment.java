package com.atouchofjoe.ghprototye4.location.info;

import android.content.Context;
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

import com.atouchofjoe.ghprototye4.LocationInfoActivity;
import com.atouchofjoe.ghprototye4.MainActivity;
import com.atouchofjoe.ghprototye4.R;
import com.atouchofjoe.ghprototye4.models.Location;
import com.atouchofjoe.ghprototye4.models.Party;


public class LocationRewardsTabFragment extends LocationTabFragment {

    private Location currentLoc;
    private Party currentParty;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        currentLoc = dummyScenarios[getArguments().getInt(LocationInfoActivity.ARG_LOCATION_NUMBER)];
        currentParty = MainActivity.partyMap.get(getArguments().getString(LocationInfoActivity.ARG_PARTY_NAME));

        if (currentParty.getLocationCompleted(currentLoc)) {

            rootView = inflater.inflate(R.layout.fragment_scenario_rewards_tab, container, false);
            // unlocked scenarios
            activateSpinner(currentLoc.getLocationsUnlocked().length == 0,
                    R.id.scenariosUnlockedTableRow, R.id.scenariosUnlockedSpinnerButton,
                    R.id.scenariosUnlockedLabelButton, scenariosUnlockedOnClickListener);

            // blocked scenarios
            activateSpinner(currentLoc.getLocationsBlocked().length == 0,
                    R.id.scenariosBlockedTableRow, R.id.scenariosBlockedSpinnerButton,
                    R.id.scenariosBlockedLabelButton, scenariosBlockedOnClickListener);

            // global achievements gained
            activateSpinner(currentLoc.getGlobalAchievementsGained().length == 0,
                    R.id.globalGainedTableRow, R.id.globalGainedSpinnerButton,
                    R.id.globalGainedLabelButton, globalGainedOnClickListener);

            // global achievements lost
            activateSpinner(currentLoc.getGlobalAchievementsLost().length == 0,
                    R.id.globalLostTableRow, R.id.globalLostSpinnerButton,
                    R.id.globalLostLabelButton, globalLostOnClickListener);

            // party achievements gained
            activateSpinner(currentLoc.getPartyAchievementsGained().length == 0,
                    R.id.partyGainedTableRow, R.id.partyGainedSpinnerButton,
                    R.id.partyGainedLabelButton, partyGainedOnClickListener);

            // party achievements lost
            activateSpinner(currentLoc.getPartyAchievementsLost().length == 0,
                    R.id.partyLostTableRow, R.id.partyLostSpinnerButton,
                    R.id.partyLostLabelButton, partyLostOnClickListener);

            // additional rewards gained
            activateSpinner(currentLoc.getAddRewards().length == 0,
                    R.id.addRewardsTableRow, R.id.addRewardsSpinnerButton,
                    R.id.addRewardsLabelButton, extrasGainedOnClickListener);

            // additional rewards lost
            activateSpinner(currentLoc.getAddPenalties().length == 0,
                    R.id.extrasLostTableRow, R.id.extrasLostSpinnerButton,
                    R.id.extrasLostLabelButton, extrasLostOnClickListener);
        } else {
            rootView = inflater.inflate(R.layout.content_empty_tab, container, false);

            TextView notCompleteMessage = rootView.findViewById(R.id.emptyMessage);
            notCompleteMessage.setText(R.string.text_not_completed);
        }
        return rootView;
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

        private void updateSpinner(AppCompatImageButton spinner, RecyclerView rView, String[] list) {
            LinearLayout.LayoutParams params= ((LinearLayout.LayoutParams) rView.getLayoutParams());
            if (params.weight == 0) {
                params.weight = list.length;
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
                    setupRecyclerView(rView, currentLoc.getLocationsUnlocked());
                    updateSpinner(spinner, rView, currentLoc.getLocationsUnlocked());
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
                    setupRecyclerView(rView, currentLoc.getLocationsBlocked());
                    updateSpinner(spinner, rView, currentLoc.getLocationsBlocked());
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
                    setupRecyclerView(rView, currentLoc.getGlobalAchievementsGained());
                    updateSpinner(spinner, rView, currentLoc.getGlobalAchievementsGained());
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
                    setupRecyclerView(rView, currentLoc.getGlobalAchievementsLost());
                    updateSpinner(spinner, rView, currentLoc.getGlobalAchievementsLost());
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
                    setupRecyclerView(rView, currentLoc.getPartyAchievementsGained());
                    updateSpinner(spinner, rView, currentLoc.getPartyAchievementsGained());
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
                    setupRecyclerView(rView, currentLoc.getPartyAchievementsLost());
                    updateSpinner(spinner, rView, currentLoc.getGlobalAchievementsLost());
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
                    setupRecyclerView(rView, currentLoc.getAddRewards());
                    updateSpinner(spinner, rView, currentLoc.getAddRewards());
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
                    setupRecyclerView(rView, currentLoc.getAddPenalties());
                    updateSpinner(spinner, rView, currentLoc.getAddPenalties());
                }
            };

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, String[] data) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(getActivity(), data));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final String[] mValues;
        private LayoutInflater mInflater;


        private SimpleItemRecyclerViewAdapter(Context context, String[] data){

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
            holder.button.setText(mValues[position]);

        }

        @Override
        public int getItemCount() {
            return mValues.length;
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
