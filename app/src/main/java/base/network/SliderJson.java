package base.network;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christian on 4/7/18.
 */

public class SliderJson {
    public class Callback{
        private List<Slider> l = new ArrayList<>();
        private String rs;

        public String getRs() {
            return rs;
        }

        public List<Slider> getL() {
            return l;
        }

        public class Slider{
            private Long id;
            private String name;
            private String image;
            private String link;
            private String publish;
            private String package_name;

            public String getPackage_name() {
                return package_name;
            }

            public void setPackage_name(String package_name) {
                this.package_name = package_name;
            }

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getPublish() {
                return publish;
            }

            public void setPublish(String publish) {
                this.publish = publish;
            }
        }
    }
}
