public interface BorrowerServices {
    public void borrowBooks(Borrower borrower);

    public void returnBook(Borrower borrower);

    public void payFine(Borrower borrower);

    public int calculateFine(long days);

}
