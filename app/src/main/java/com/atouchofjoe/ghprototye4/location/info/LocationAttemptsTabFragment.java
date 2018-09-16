package com.atouchofjoe.ghprototye4.location.info;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.atouchofjoe.ghprototye4.MainActivity;
import com.atouchofjoe.ghprototye4.R;
import com.atouchofjoe.ghprototye4.RecordAttemptActivity;
import com.atouchofjoe.ghprototye4.models.Attempt;
import com.atouchofjoe.ghprototye4.models.Location;
import com.atouchofjoe.ghprototye4.models.Party;
import com.atouchofjoe.ghprototye4.party.info.EditPartyActivity;

import java.util.List;

import static com.atouchofjoe.ghprototye4.location.info.LocationInfoActivity.locations;


public class LocationAttemptsTabFragment extends LocationTabFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Location currentLocation =  locations[getArguments().getInt(LocationInfoActivity.ARG_LOCATION_NUMBER, 0)];
        Party currentParty = MainActivity.currentParty;

        View rootView;
            if(currentLocation.getNumber() == 0) {
                rootView = inflater.inflate(R.layout.content_empty_tab, container, false);

                TextView noAttemptsMessage = rootView.findViewById(R.id.emptyMessage);
                noAttemptsMessage.setText(R.string.text_no_attempts);
            }
            else if(currentParty.getLocationAttempted(currentLocation.getNumber())) {
                rootView = inflater.inflate(R.layout.fragment_scenario_attempts_tab, container, false);
                setupRecyclerView((RecyclerView)rootView, currentParty.getLocationAttemptsForLocation(currentLocation.getNumber()));
            }
            else {
                rootView = inflater.inflate(R.layout.content_add_attempt_tab, container, false);

                Button recordAttemptButton = rootView.findViewById(R.id.recordAttempt);
                recordAttemptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {

                        if(MainActivity.currentParty.getCharacters().size() < 2) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setMessage("You do not have enough party members to save an attempt!");
                            builder.setPositiveButton("Edit Party", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(view.getContext(), EditPartyActivity.class);
                                    startActivity(intent);
                                }
                            });
                            builder.setNegativeButton("Go Back", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                            builder.create().show();
                        }
                        else {
                            Intent intent = new Intent(view.getContext(), RecordAttemptActivity.class);
                            intent.putExtra(RecordAttemptActivity.ARG_LOCATION_NUMBER, currentLocation.getNumber());
                            startActivity(intent);
                        }
                    }
                });
            }
        return rootView;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, List<Attempt> attempts) {
        recyclerView.setAdapter(new AttemptsRecyclerViewAdapter(getActivity(), attempts));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public static class AttemptsRecyclerViewAdapter
            extends RecyclerView.Adapter<AttemptsRecyclerViewAdapter.ViewHolder> {

        private final List<Attempt> mValues;
        private LayoutInflater mInflater;
        private Context mContext;

        private AttemptsRecyclerViewAdapter(Context context, List<Attempt> data){
            mInflater = LayoutInflater.from(context);
            mValues = data;
            mContext = context;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.content_attempt_detail, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            Attempt attempt = mValues.get(position);
            holder.date.setText(attempt.getTimestamp().toString());

            ArrayAdapter<String> participantsSpinnerAdapter
                    = new ArrayAdapter<>(mContext,
                                                android.R.layout.simple_selectable_list_item,
                                                attempt.getParticipants());
            participantsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.participantsSpinner.setAdapter(participantsSpinnerAdapter);

            ArrayAdapter<String> nonParticipantsSpinnerAdapter
                    = new ArrayAdapter<>(mContext,
                                        android.R.layout.simple_selectable_list_item,
                                        attempt.getNonParticipants());
            nonParticipantsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.nonParticipantsSpinner.setAdapter(nonParticipantsSpinnerAdapter);

            holder.attemptSuccessful.setText(attempt.getAttemptSuccessful() ?
                    R.string.value_yes : R.string.value_no);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView date;
            final Spinner participantsSpinner;
            final Spinner nonParticipantsSpinner;
            final ImageButton editParticipantsButton;
            final TextView attemptSuccessful;


            ViewHolder(View view) {
                super(view);
                date = view.findViewById(R.id.dateAttemptedValue);
                participantsSpinner = view.findViewById((R.id.participatsSpinner));
                nonParticipantsSpinner = view.findViewById(R.id.nonParticipatingSpinner);
                editParticipantsButton = view.findViewById(R.id.editParticipantsButton);
                editParticipantsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO what?
                    }
                });
                attemptSuccessful = view.findViewById(R.id.attemptSuccssfulValue);

            }
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

    }
}

