package com.example.kisaanonline.Models;

import java.util.List;

public class CheckoutCredentials {

    private String paymentMode;
    private Integer paymentAmount, taxAmount, subAmount;
    List<OrderDetails> orderList;

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Integer getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Integer paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Integer getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Integer taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Integer getSubAmount() {
        return subAmount;
    }

    public void setSubAmount(Integer subAmount) {
        this.subAmount = subAmount;
    }

    public List<OrderDetails> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderDetails> orderList) {
        this.orderList = orderList;
    }

    public class OrderDetails{

        private String variantid;
        private String productid;
        private Integer qty;

        public OrderDetails(String variantid, String productid, Integer  qty) {
            this.variantid = variantid;
            this.productid = productid;
            this.qty = qty;
        }

        public String getVariantid() {
            return variantid;
        }

        public void setVariantid(String variantid) {
            this.variantid = variantid;
        }

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public Integer getQty() {
            return qty;
        }

        public void setQty(Integer qty) {
            this.qty = qty;
        }
    }



}
