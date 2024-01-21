import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICupomDesconto } from '../cupom-desconto.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../cupom-desconto.test-samples';

import { CupomDescontoService } from './cupom-desconto.service';

const requireRestSample: ICupomDesconto = {
  ...sampleWithRequiredData,
};

describe('CupomDesconto Service', () => {
  let service: CupomDescontoService;
  let httpMock: HttpTestingController;
  let expectedResult: ICupomDesconto | ICupomDesconto[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CupomDescontoService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a CupomDesconto', () => {
      const cupomDesconto = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cupomDesconto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CupomDesconto', () => {
      const cupomDesconto = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cupomDesconto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CupomDesconto', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CupomDesconto', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CupomDesconto', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCupomDescontoToCollectionIfMissing', () => {
      it('should add a CupomDesconto to an empty array', () => {
        const cupomDesconto: ICupomDesconto = sampleWithRequiredData;
        expectedResult = service.addCupomDescontoToCollectionIfMissing([], cupomDesconto);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cupomDesconto);
      });

      it('should not add a CupomDesconto to an array that contains it', () => {
        const cupomDesconto: ICupomDesconto = sampleWithRequiredData;
        const cupomDescontoCollection: ICupomDesconto[] = [
          {
            ...cupomDesconto,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCupomDescontoToCollectionIfMissing(cupomDescontoCollection, cupomDesconto);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CupomDesconto to an array that doesn't contain it", () => {
        const cupomDesconto: ICupomDesconto = sampleWithRequiredData;
        const cupomDescontoCollection: ICupomDesconto[] = [sampleWithPartialData];
        expectedResult = service.addCupomDescontoToCollectionIfMissing(cupomDescontoCollection, cupomDesconto);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cupomDesconto);
      });

      it('should add only unique CupomDesconto to an array', () => {
        const cupomDescontoArray: ICupomDesconto[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cupomDescontoCollection: ICupomDesconto[] = [sampleWithRequiredData];
        expectedResult = service.addCupomDescontoToCollectionIfMissing(cupomDescontoCollection, ...cupomDescontoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cupomDesconto: ICupomDesconto = sampleWithRequiredData;
        const cupomDesconto2: ICupomDesconto = sampleWithPartialData;
        expectedResult = service.addCupomDescontoToCollectionIfMissing([], cupomDesconto, cupomDesconto2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cupomDesconto);
        expect(expectedResult).toContain(cupomDesconto2);
      });

      it('should accept null and undefined values', () => {
        const cupomDesconto: ICupomDesconto = sampleWithRequiredData;
        expectedResult = service.addCupomDescontoToCollectionIfMissing([], null, cupomDesconto, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cupomDesconto);
      });

      it('should return initial array if no CupomDesconto is added', () => {
        const cupomDescontoCollection: ICupomDesconto[] = [sampleWithRequiredData];
        expectedResult = service.addCupomDescontoToCollectionIfMissing(cupomDescontoCollection, undefined, null);
        expect(expectedResult).toEqual(cupomDescontoCollection);
      });
    });

    describe('compareCupomDesconto', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCupomDesconto(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCupomDesconto(entity1, entity2);
        const compareResult2 = service.compareCupomDesconto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCupomDesconto(entity1, entity2);
        const compareResult2 = service.compareCupomDesconto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCupomDesconto(entity1, entity2);
        const compareResult2 = service.compareCupomDesconto(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
