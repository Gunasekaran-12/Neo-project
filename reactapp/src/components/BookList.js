public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {

    Optional<BorrowRecord> findFirstByBookAndReturnDateIsNullOrderByBorrowDateDesc(Book book);

    boolean existsByBookAndReturnDateIsNull(Book book);

    List<BorrowRecord> findByBorrower(Borrower borrower);
}
