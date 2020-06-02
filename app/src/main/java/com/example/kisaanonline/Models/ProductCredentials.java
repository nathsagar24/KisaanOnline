package com.example.kisaanonline.Models;

import java.util.List;

    public class ProductCredentials {
        private String productid, variantid;
        private int quantity;

        public ProductCredentials(String productid, String variantid, int quantity) {
            this.productid = productid;
            this.variantid = variantid;
            this.quantity = quantity;
        }

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public String getVariantid() {
            return variantid;
        }

        public void setVariantid(String variantid) {
            this.variantid = variantid;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
