package com.turtleplayer.playlist.playorder;

import ch.hoene.perzist.access.executor.OperationExecutor;
import ch.hoene.perzist.access.sort.RandomOrder;
import ch.hoene.perzist.android.FirstSqlLite;
import ch.hoene.perzist.android.QuerySqlite;
import com.turtleplayer.model.Track;
import com.turtleplayer.persistance.turtle.db.TurtleDatabase;
import com.turtleplayer.persistance.turtle.db.structure.Tables;
import com.turtleplayer.persistance.turtle.mapping.TrackCreator;
import com.turtleplayer.playlist.Playlist;

public class PlayOrderRandom implements PlayOrderStrategy
{

	private final Playlist playlist;
	private final TurtleDatabase db;

	public PlayOrderRandom(TurtleDatabase db,
								  Playlist playlist)
	{
		this.playlist = playlist;
		this.db = db;
	}

	public Track getNext(Track currTrack)
	{
		return get();
	}

	public Track getPrevious(Track currTrack)
	{
		return get();
	}

	private Track get()
	{
		return OperationExecutor.execute(db,
				  new QuerySqlite<Tables.Tracks, Tables.Tracks, Track>(
							 playlist.getCompressedFilter(),
							 new RandomOrder<Tables.Tracks>(),
							 new FirstSqlLite<Track>(Tables.TRACKS, new TrackCreator())));
	}
}
