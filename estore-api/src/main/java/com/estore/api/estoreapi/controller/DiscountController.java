package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.DiscountDAO;
import com.estore.api.estoreapi.model.Discount;
import com.estore.api.estoreapi.model.Enums.UserType;

@RestController
@RequestMapping("discount")
public class DiscountController {
    private static final Logger LOG = Logger.getLogger(DiscountController.class.getName());
    private DiscountDAO discountDao;
    private LoginController loginController;

    public DiscountController(DiscountDAO discountDao, LoginController loginController){
        this.discountDao = discountDao;
        this.loginController = loginController;
    }

    /**
     * Responds to the GET request for a {@linkplain Discount discount} for the given id
     * 
     * @param id The id used to locate the {@link Discount discount}
     * 
     * @return ResponseEntity with {@link Discount discount} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Discount> getDiscount(@PathVariable int id) {
        LOG.info("GET /discounts/" + id);
        try {
            Discount discount = discountDao.getDiscount(id);
            if (discount != null)
                return new ResponseEntity<Discount>(discount,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Discount[]> getDiscounts(){
        try {
            return new ResponseEntity<Discount[]>(discountDao.getDiscounts(),HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<Discount> createDiscount(@RequestBody Discount discount) {
        LOG.info("POST /discounts " + discount);
        // LOG.info("current user " + loginController.currentUser);
        // Replace below with your implementation
        if(loginController.currentUser.getUserType() != UserType.Admin){
            LOG.info("NOT LOGGED INTO ADMIN");
            return new ResponseEntity<>(HttpStatus.PROXY_AUTHENTICATION_REQUIRED);
        }
        try {
            if (discountDao.getDiscount(discount.getId()) != null)
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            else{
                Discount newDiscount = discountDao.createDiscount(discount);
                return new ResponseEntity<Discount>(newDiscount,HttpStatus.CREATED);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Discount discount} with the provided {@linkplain Discount discount} object, if it exists
     * 
     * @param discount The {@link Discount discount} to update
     * 
     * @return ResponseEntity with updated {@link Discount discount} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Discount> updateDiscount(@RequestBody Discount discount) {
        LOG.info("PUT /discounts " + discount);

        // Replace below with your implementation
        if(loginController.currentUser.getUserType() != UserType.Admin){
            LOG.info("NOT LOGGED INTO ADMIN");
            return new ResponseEntity<>(HttpStatus.PROXY_AUTHENTICATION_REQUIRED);
        }
        try {
            discount = discountDao.updateDiscount(discount);
            if (discount != null){
                return new ResponseEntity<Discount>(discount,HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Discount discount} with the given id
     * 
     * @param id The id of the {@link Discount discount} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Discount> deleteDiscount(@PathVariable int id) {
        LOG.info("DELETE /discounts/" + id);

        // Replace below with your implementation
        if(loginController.currentUser.getUserType() != UserType.Admin){
            LOG.info("NOT LOGGED INTO ADMIN");
        return new ResponseEntity<>(HttpStatus.PROXY_AUTHENTICATION_REQUIRED);
        }
        try {
            if (discountDao.deleteDiscount(id)){
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
