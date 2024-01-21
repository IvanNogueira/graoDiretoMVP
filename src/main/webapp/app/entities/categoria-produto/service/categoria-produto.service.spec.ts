import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICategoriaProduto } from '../categoria-produto.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../categoria-produto.test-samples';

import { CategoriaProdutoService } from './categoria-produto.service';

const requireRestSample: ICategoriaProduto = {
  ...sampleWithRequiredData,
};

describe('CategoriaProduto Service', () => {
  let service: CategoriaProdutoService;
  let httpMock: HttpTestingController;
  let expectedResult: ICategoriaProduto | ICategoriaProduto[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CategoriaProdutoService);
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

    it('should create a CategoriaProduto', () => {
      const categoriaProduto = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(categoriaProduto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CategoriaProduto', () => {
      const categoriaProduto = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(categoriaProduto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CategoriaProduto', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CategoriaProduto', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CategoriaProduto', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCategoriaProdutoToCollectionIfMissing', () => {
      it('should add a CategoriaProduto to an empty array', () => {
        const categoriaProduto: ICategoriaProduto = sampleWithRequiredData;
        expectedResult = service.addCategoriaProdutoToCollectionIfMissing([], categoriaProduto);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(categoriaProduto);
      });

      it('should not add a CategoriaProduto to an array that contains it', () => {
        const categoriaProduto: ICategoriaProduto = sampleWithRequiredData;
        const categoriaProdutoCollection: ICategoriaProduto[] = [
          {
            ...categoriaProduto,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCategoriaProdutoToCollectionIfMissing(categoriaProdutoCollection, categoriaProduto);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CategoriaProduto to an array that doesn't contain it", () => {
        const categoriaProduto: ICategoriaProduto = sampleWithRequiredData;
        const categoriaProdutoCollection: ICategoriaProduto[] = [sampleWithPartialData];
        expectedResult = service.addCategoriaProdutoToCollectionIfMissing(categoriaProdutoCollection, categoriaProduto);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(categoriaProduto);
      });

      it('should add only unique CategoriaProduto to an array', () => {
        const categoriaProdutoArray: ICategoriaProduto[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const categoriaProdutoCollection: ICategoriaProduto[] = [sampleWithRequiredData];
        expectedResult = service.addCategoriaProdutoToCollectionIfMissing(categoriaProdutoCollection, ...categoriaProdutoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const categoriaProduto: ICategoriaProduto = sampleWithRequiredData;
        const categoriaProduto2: ICategoriaProduto = sampleWithPartialData;
        expectedResult = service.addCategoriaProdutoToCollectionIfMissing([], categoriaProduto, categoriaProduto2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(categoriaProduto);
        expect(expectedResult).toContain(categoriaProduto2);
      });

      it('should accept null and undefined values', () => {
        const categoriaProduto: ICategoriaProduto = sampleWithRequiredData;
        expectedResult = service.addCategoriaProdutoToCollectionIfMissing([], null, categoriaProduto, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(categoriaProduto);
      });

      it('should return initial array if no CategoriaProduto is added', () => {
        const categoriaProdutoCollection: ICategoriaProduto[] = [sampleWithRequiredData];
        expectedResult = service.addCategoriaProdutoToCollectionIfMissing(categoriaProdutoCollection, undefined, null);
        expect(expectedResult).toEqual(categoriaProdutoCollection);
      });
    });

    describe('compareCategoriaProduto', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCategoriaProduto(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCategoriaProduto(entity1, entity2);
        const compareResult2 = service.compareCategoriaProduto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCategoriaProduto(entity1, entity2);
        const compareResult2 = service.compareCategoriaProduto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCategoriaProduto(entity1, entity2);
        const compareResult2 = service.compareCategoriaProduto(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
