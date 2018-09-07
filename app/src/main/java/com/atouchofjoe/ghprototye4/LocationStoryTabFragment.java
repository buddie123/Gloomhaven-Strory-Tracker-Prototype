package com.atouchofjoe.ghprototye4;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atouchofjoe.ghprototye4.Dummy.DummyContent;
import com.atouchofjoe.ghprototye4.models.Location;
import com.atouchofjoe.ghprototye4.models.Party;


public class LocationStoryTabFragment extends LocationTabFragment {



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scenario_story_tab, container, false);

        Location currentLoc = dummyScenarios[getArguments().getInt(LocationInfoActivity.ARG_LOCATION_NUMBER)];
        Party currentParty = MainActivity.partyMap.get(getArguments().getString(LocationInfoActivity.ARG_PARTY_NAME));

        // set view
        if(currentParty.getLocationCompleted(currentLoc)) {
            // hide teaser
            view.findViewById(R.id.locTeaserLabelTableRow).setVisibility(View.GONE);
            view.findViewById(R.id.locTeaserTableRow).setVisibility(View.GONE);

            // hide summary
            view.findViewById(R.id.locSummaryLabelTableRow).setVisibility(View.GONE);
            view.findViewById(R.id.locSummaryTableRow).setVisibility(View.GONE);

            // update conclusion
            TextView sConclusion = view.findViewById(R.id.locConclusionTextView);
            sConclusion.setText(currentLoc.getConclusion());
        }
        else if(currentParty.getLocationAttempted(currentLoc)) {

            // show teaser TODO - figure out if this is the right behavior
            TextView sTeaser = view.findViewById(R.id.locTeaserTextView);
            sTeaser.setText(currentLoc.getTeaser());

            // show summary
            TextView sSummary = view.findViewById(R.id.lastScenarioSummaryTextView);
            sSummary.setText(currentLoc.getSummary());

            // hide conclusion
            view.findViewById(R.id.locConclusionLabelTableRow).setVisibility(View.GONE);
            view.findViewById(R.id.locConclusionTableRow).setVisibility(View.GONE);
        }
        else { // not attempted and not completed
            //show teaser
            TextView sTeaser = view.findViewById(R.id.locTeaserTextView);
            sTeaser.setText(currentLoc.getTeaser());

            // hide summary
            view.findViewById(R.id.locSummaryLabelTableRow).setVisibility(View.GONE);
            view.findViewById(R.id.locSummaryTableRow).setVisibility(View.GONE);

            // hide conclusion
            view.findViewById(R.id.locConclusionLabelTableRow).setVisibility(View.GONE);
            view.findViewById(R.id.locConclusionTableRow).setVisibility(View.GONE);
        }
        return view;
    }
}
