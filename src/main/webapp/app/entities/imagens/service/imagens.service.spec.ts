import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IImagens } from '../imagens.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../imagens.test-samples';

import { ImagensService } from './imagens.service';

const requireRestSample: IImagens = {
  ...sampleWithRequiredData,
};

describe('Imagens Service', () => {
  let service: ImagensService;
  let httpMock: HttpTestingController;
  let expectedResult: IImagens | IImagens[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ImagensService);
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

    it('should create a Imagens', () => {
      const imagens = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(imagens).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Imagens', () => {
      const imagens = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(imagens).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Imagens', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Imagens', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Imagens', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addImagensToCollectionIfMissing', () => {
      it('should add a Imagens to an empty array', () => {
        const imagens: IImagens = sampleWithRequiredData;
        expectedResult = service.addImagensToCollectionIfMissing([], imagens);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(imagens);
      });

      it('should not add a Imagens to an array that contains it', () => {
        const imagens: IImagens = sampleWithRequiredData;
        const imagensCollection: IImagens[] = [
          {
            ...imagens,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addImagensToCollectionIfMissing(imagensCollection, imagens);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Imagens to an array that doesn't contain it", () => {
        const imagens: IImagens = sampleWithRequiredData;
        const imagensCollection: IImagens[] = [sampleWithPartialData];
        expectedResult = service.addImagensToCollectionIfMissing(imagensCollection, imagens);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(imagens);
      });

      it('should add only unique Imagens to an array', () => {
        const imagensArray: IImagens[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const imagensCollection: IImagens[] = [sampleWithRequiredData];
        expectedResult = service.addImagensToCollectionIfMissing(imagensCollection, ...imagensArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const imagens: IImagens = sampleWithRequiredData;
        const imagens2: IImagens = sampleWithPartialData;
        expectedResult = service.addImagensToCollectionIfMissing([], imagens, imagens2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(imagens);
        expect(expectedResult).toContain(imagens2);
      });

      it('should accept null and undefined values', () => {
        const imagens: IImagens = sampleWithRequiredData;
        expectedResult = service.addImagensToCollectionIfMissing([], null, imagens, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(imagens);
      });

      it('should return initial array if no Imagens is added', () => {
        const imagensCollection: IImagens[] = [sampleWithRequiredData];
        expectedResult = service.addImagensToCollectionIfMissing(imagensCollection, undefined, null);
        expect(expectedResult).toEqual(imagensCollection);
      });
    });

    describe('compareImagens', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareImagens(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareImagens(entity1, entity2);
        const compareResult2 = service.compareImagens(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareImagens(entity1, entity2);
        const compareResult2 = service.compareImagens(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareImagens(entity1, entity2);
        const compareResult2 = service.compareImagens(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
