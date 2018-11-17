package com.example.rocio.meetandtravel.viewcontrollers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;
import com.example.rocio.meetandtravel.R;
import com.example.rocio.meetandtravel.models.Event;

import java.util.ArrayList;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> implements Filterable {

    private List<Event> events;
    private List<Event> eventsFiltered;
    private EventsAdapter.FilterEvents filterEvents;
    private Context context;
    private EventsAdapter.OnEventClickListener onEventClickListener;

    public EventsAdapter(EventsAdapter.OnEventClickListener onEventClickListener, List<Event> events, Context context) {
        this.events = events;
        this.eventsFiltered = events;
        this.onEventClickListener = onEventClickListener;
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public EventsAdapter setContext(Context context) {
        this.context = context;
        return this;
    }

    public EventsAdapter.OnEventClickListener getOnEventClickListener() {
        return onEventClickListener;
    }

    public EventsAdapter setOnEventClickListener(EventsAdapter.OnEventClickListener onEventClickListener) {
        this.onEventClickListener = onEventClickListener;
        return this;
    }

    public interface OnEventClickListener{
        void onClick(Event event);
    }

    @NonNull
    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new EventsAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_event, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.updateFrom(eventsFiltered.get(i));
    }

    @Override
    public int getItemCount() {
        return eventsFiltered.size();
    }

    @Override
    public Filter getFilter() {
        if (filterEvents == null) { filterEvents = new FilterEvents();}
        return filterEvents;

    }

    public List<Event> getEvents() {
        return events;
    }

    public EventsAdapter setEvents(List<Event> events) {
        this.events = events;
        this.eventsFiltered = events;
        return this;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ANImageView imageView;
        private TextView name, date ,location;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.eventImageView);
            name = itemView.findViewById(R.id.nameEventTextView);
            date = itemView.findViewById(R.id.dateEventTextView);
            location = itemView.findViewById(R.id.locationEventTextView);
            cardView = itemView.findViewById(R.id.eventLayout);
        }

        private void updateFrom(final Event event) {
            imageView.setDefaultImageResId(R.mipmap.ic_launcher);
            imageView.setErrorImageResId(R.mipmap.ic_launcher);
            imageView.setImageUrl(event.getEventImage());
            name.setText(event.getName());
            date.setText(event.getStartDate());
            location.setText(event.getLocation());
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEventClickListener.onClick(event);
                }
            });
        }
    }

    public class FilterEvents extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if(constraint != null && constraint.length()>0) {
                List<Event> temp = new ArrayList<>();
                for (Event event : events){
                    if (event.getName().toLowerCase().contains(constraint.toString().toLowerCase())){
                        temp.add(event);
                        Log.d("Filtrado", event.getName());
                    }
                }
                Log.d("Filtro", constraint.toString());

                eventsFiltered = temp;
            }else {
                eventsFiltered = events;
            }
            filterResults.count = eventsFiltered.size();
            filterResults.values = eventsFiltered;
            return  filterResults;

        }
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            eventsFiltered = (List<Event>) results.values;
            notifyDataSetChanged();

        }
    }


    }




