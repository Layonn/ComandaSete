package fafit.com.br.comandasete;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter<Category> {
    ArrayList<Category> categoryList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    public CategoryAdapter(Context context, int resource, ArrayList<Category> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        categoryList = objects;
    }


    static class ViewHolder {
        public ImageView image;
        public TextView txtName;
        public TextView txtId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();

            v = vi.inflate(Resource, null);

            holder.image = (ImageView) v.findViewById(R.id.image);

            holder.txtName = (TextView) v.findViewById(R.id.txtName);

            holder.txtId = (TextView) v.findViewById(R.id.txtId);

            v.setTag(holder);

        } else {

            holder = (ViewHolder) v.getTag();
        }

        holder.image.setImageResource(R.drawable.ic_launcher);

        new DownloadImageTask(holder.image).execute(categoryList.get(position).getImage());

        holder.txtName.setText(categoryList.get(position).getId());

        holder.txtId.setText(categoryList.get(position).getNome());

        return v;

    }

    //###############AsyncTask######################
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
