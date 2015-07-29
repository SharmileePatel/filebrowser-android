This library project provides a simple component based file browser that extends a ListView.

Objectives:<br>
- easy to integreate<br>
- interface callbacks<br>
- easy to expand<br>
<br>
Features:<br>
- multi attribute sorting with a ComparatorChain<br>
(default: sort directory and files than by name)<br>
<br>
Check out the demo app on <a href='https://play.google.com/store/apps/details?id=de.so.filebrowser'>play.google.com</a>

<a href='http://code.google.com/p/filebrowser-android/source/browse/trunk/icons_org.so.filebrowser.ai'>Illustrator 5 file for icons</a>
<br>

<a href='http://filebrowser-android.googlecode.com/files/examplescreen.PNG'>http://filebrowser-android.googlecode.com/files/examplescreen.PNG</a>

Just add the full qualified FileListView into your layout:<br>
<pre><code>&lt;org.so.filebrowser.FileListView
        android:id="@+id/fileListView"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1" /&gt;
</code></pre>

The onCreate()-Method should look like:<br>
<br>
<pre><code>FileListView fileList = (FileListView) findViewById(R.id.fileListView);
fileList.setOnDirectoryOrFileClickListener(new FileListView.OnDirectoryOrFileClickListener() {
	public void onDirectoryOrFileClick(File file) {
		System.out.println(file);
	}
});

TextView textViewDirectory = (TextView) findViewById(R.id.textViewDirectory);
fileList.setTextViewDirectory(textViewDirectory);

TextView textViewFile = (TextView) findViewById(R.id.textViewFile);
fileList.setTextViewFile(textViewFile);

EditText editTextFile = (EditText) findViewById(R.id.editTextFile);
fileList.setEditTextFile(editTextFile);

fileList.init();
</code></pre>
The init() must get called at the end of all the configuration.<br>
<br><br><br>
For custom icons do the following:<br>
<pre><code>FileSystemAdapter adapter = fileListView.getAdapter();
adapter.addExtention("jpg", R.drawable.youricon);
</code></pre>
The extentions "folder" and "file" are used for ... folders and files!<br>
<br>
<br><br><br>
<a href='http://strangeoptics.blogspot.de/'>strangeoptics blog</a>