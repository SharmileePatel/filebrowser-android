package org.so.filebrowser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.so.filebrowser.R;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FileSystemAdapter extends ArrayAdapter<FileData> {

	private String root;
	private List<String> paths = new ArrayList<String>();
	private File path;
	private Map<String, Integer> mapExtentions = new HashMap<String, Integer>();
	
	public FileSystemAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId, new ArrayList<FileData>());
		root = Environment.getExternalStorageDirectory().getPath();
		path = new File(root + "/");
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
        TextView textView;
        ImageView imageView;
        
        if (convertView == null) {
            view = mInflater.inflate(mResource, parent, false);
        } else {
            view = convertView;
        }
        
        try {
	        textView = (TextView) view.findViewById(R.id.textFileName);
	        imageView = (ImageView) view.findViewById(R.id.imageFileType);
        } catch(ClassCastException e) {
        	Log.e("ArrayAdapter", "You must supply a resource ID for a TextView");
        	throw new IllegalStateException(
                  "ArrayAdapter requires the resource ID to be a TextView", e);
        }
        
        FileData item = getItem(position);
        textView.setText((CharSequence)item.name);
        
        if(item.directory) {
        	imageView.setImageResource(R.drawable.folder);
        } else {
        	String fileExtention = getFileExtention(item.name);
        	Integer resource = mapExtentions.get(fileExtention);
        	
        	if(resource != null) {
        		imageView.setImageResource(resource);
        	} else {
        		imageView.setImageResource(R.drawable.file);
        	}
        }
        
        return view;
	}
	
	/**
	 * Primitive extraction of the file extention in lower case. <br>
	 * file.ext -> ext <br>
	 * If there is no '.' in the file name, null is returned!
	 * @param fileName
	 * @return extention or null
	 */
	private String getFileExtention(String fileName) {
		int lastIndexOfPoint = fileName.lastIndexOf('.');
		if(lastIndexOfPoint != -1) {
			return fileName.substring(lastIndexOfPoint+1).toLowerCase();
		}
		return null;
	}
	
	
	/**
	 * Reflect the folder structure to the ui
	 */
	public void showFileSystem() {
		List<FileData> items = new ArrayList<FileData>();
		File[] files = path.listFiles();

		if (!path.getAbsolutePath().equals(root)) {
			items.add(new FileData("../", true));
		}

		for (int i = 0; i < files.length; i++) {
			File file = files[i];

			if (!file.isHidden() && file.canRead()) {
				if (file.isDirectory()) {
					items.add(new FileData(file.getName() + "/", true));
				} else {
					items.add(new FileData(file.getName(), false));
				}
			}
		}

		clearNoNotification();
		addAll(items);
	}
	
	public String getRoot() {
		return root;
	}
	
	/**
	 * Moves the Adapter to the next folder: <br>
	 * 1. "../" -> one folder back <br>
	 * 2. "foldername/" -> to this folder <br>
	 * 3. "file.dat" -> don't move
	 * @param pathSuffix suffix to the path like "images/"
	 * @return returns the next folder or the same in case of a file
	 */
	public File move(String pathSuffix) {
		if(pathSuffix.equalsIgnoreCase("../")) {
			paths.remove(paths.size()-1);
		} else {
			paths.add(pathSuffix);
		}
		String filePath = root + "/";
		for(String p : paths) {
			filePath += p;
		}
		
		File file = new File(filePath);
		if(file.isDirectory()) {
			path = file;
		} else {
			paths.remove(paths.size()-1);
		}
		return file;
	}
	
	/**
	 * 
	 * @param ext in lower case without a point e.g. "zip"
	 * @param resource R.drawable.xxx
	 */
	public void addExtention(String ext, int resource) {
		mapExtentions.put(ext, Integer.valueOf(resource));
	}
	
	public void clearExtentionMapping() {
		mapExtentions.clear();
	}
	
	public boolean hasExtentions() {
		return !mapExtentions.isEmpty();
	}
	
	public File getPath() {
		return path;
	}
}