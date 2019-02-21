package dbo.Viewer;

public class ConvertedTimestamp {
	boolean outside;
	long timestamp;

	public ConvertedTimestamp(long timestamp, boolean outside) {
		this.timestamp = timestamp;
		this.outside = outside;
	}

	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * 
	 * @return if the timestamp is not present in the video
	 */
	public boolean isOutside() {
		return outside;
	}
}
