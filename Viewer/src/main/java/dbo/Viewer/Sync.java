package dbo.Viewer;

public class Sync {
	private long[] durations, offsets;
	private long maxStartTimestamp, syncDuration;
	private int numElements, mainElement;

	/**
	 * Constructor
	 * 
	 * @param durations       Array of the duration of each element to synchronize
	 * @param startTimestamps Array of the timestamps of the start of the elements
	 * @throws Exception
	 */
	public Sync(long[] durations, long[] startTimestamps) throws Exception {
		numElements = durations.length;
		if (numElements != startTimestamps.length) {
			throw new Exception("Lengths don't match while synchronizing");
		}
		if (numElements < 2) {
			throw new Exception("Needs at least 2 arguments to synchronize");
		}

		this.durations = durations;

		// Calculate max start timestamp
		maxStartTimestamp = startTimestamps[0];
		mainElement = 0;
		for (int i = 1; i < numElements; i++) {
			if (startTimestamps[i] > maxStartTimestamp) {
				maxStartTimestamp = startTimestamps[i];
				mainElement = i;
			}
		}

		// Calculate offsets
		offsets = new long[numElements];
		for (int i = 0; i < numElements; i++) {
			offsets[i] = maxStartTimestamp - startTimestamps[i];
		}

		// Calculate sync duration
		syncDuration = durations[0] - offsets[0];
		long temp;
		for (int i = 1; i < numElements; i++) {
			temp = durations[i] - offsets[i];
			if (temp < syncDuration) {
				syncDuration = temp;
			}
		}
	}

	/**
	 * Convert a synchronized timestamp of a source element to the synchonized
	 * timestamp in the target element
	 * 
	 * @param targetElementID        ID (index of the array) of the target element
	 * @param sourceElementID        ID (index of the array) of the source element
	 * @param sourceElementTimestamp timestamp of the source element
	 * @return ConvertedTimestamp object that contains the timestamp and a boolean
	 *         that represents if the timestamp is out of the bounds
	 */
	public ConvertedTimestamp convertTimestamp(int targetElementID, int sourceElementID, long sourceElementTimestamp) {
		long convertedTimestamp = sourceElementTimestamp - offsets[sourceElementID] + offsets[targetElementID];
		boolean outside = (convertedTimestamp < 0) || (convertedTimestamp > durations[targetElementID]);
		return new ConvertedTimestamp(convertedTimestamp, outside);

	}

	/**
	 * Returns the first timestamp where all the videos can be synchronized
	 * 
	 * @param id ID of the video to get the timestamp from
	 * @return the timestamp
	 */
	public long getFirstTimestamp(int id) {
		return offsets[id];
	}

	/**
	 * Returns the duration where all elements can be synchronized
	 * 
	 * @return the duration
	 */
	public long getSynchronizedDuration() {
		return syncDuration;
	}

	/**
	 * Converts a timestamp in the synchronized duration to the local timestamp of a
	 * target element
	 * 
	 * @param targetElementID ID of the target element
	 * @param timestamp       global timestamp
	 * @return converted timestamp
	 */
	public ConvertedTimestamp convertSynchronizedTimestamp(int targetElementID, long timestamp) {
		long convertedTimestamp = offsets[targetElementID] + timestamp;
		boolean outside = (convertedTimestamp < 0) || (convertedTimestamp > durations[targetElementID]);
		return new ConvertedTimestamp(convertedTimestamp, outside);
	}

	/**
	 * Gets the ID of the element that the zero time coincides with the zero time of
	 * the synchronized duration
	 * 
	 * @return ID of the element
	 */
	public int getMainElementID() {
		return mainElement;
	}
}
